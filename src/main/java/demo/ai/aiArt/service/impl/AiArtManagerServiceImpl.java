package demo.ai.aiArt.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.dto.ImageToImageDTO;
import ai.aiArt.pojo.dto.TextToImageDTO;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.vo.AiArtGenerateImageAdminVO;
import ai.aiArt.pojo.vo.AiArtImageOnWallVO;
import ai.aiArt.pojo.vo.ImgVO;
import ai.aiChat.pojo.type.AiServiceAmountType;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.AddToImageWallDTO;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.AiUserDetailInRedisDTO;
import demo.ai.aiArt.pojo.dto.RegenerateImageDTO;
import demo.ai.aiArt.pojo.dto.SetInvalidImageAndRetunTokensDTO;
import demo.ai.aiArt.pojo.po.AiArtImageWall;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecordExample;
import demo.ai.aiArt.pojo.result.AiArtGenerateImageQueryResult;
import demo.ai.aiArt.pojo.result.GetJobResultListForReivew;
import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.aiArt.service.AiArtManagerService;
import demo.ai.aiArt.service.AiArtService;
import demo.image.pojo.result.GetImgThirdPartyUrlInBatchResult;
import demo.image.service.ImageService;
import net.sf.json.JSONObject;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;

@Service
public class AiArtManagerServiceImpl extends AiArtCommonService implements AiArtManagerService {

	@Autowired
	private AiArtService aiArtService;
	@Autowired
	private ImageService imageService;

	@Override
	public ModelAndView getManagerView() {
		ModelAndView v = new ModelAndView("aiArtJSP/aiArtManager");
		v.addObject("isRunning", aiArtCacheService.getIsRunning());
		return v;
	}

	@Override
	public GetJobResultListForReivew __getAiArtJobListForReview(AiArtJobListFilterDTO dto) {
		GetJobResultListForReivew r = new GetJobResultListForReivew();
		List<AiArtTextToImageJobRecord> jobPoList = aiArtService.getJobResultPage(dto);
		if (jobPoList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}
		AiArtGenerateImageQueryResult jobResult = null;
		List<AiArtGenerateImageAdminVO> jobResultVoList = new ArrayList<>();
		Set<Long> aiUserIdSet = new HashSet<>();
		for (AiArtTextToImageJobRecord po : jobPoList) {
			jobResult = getJobResult(po.getId());
			aiUserIdSet.add(po.getAiUserId());
			AiArtGenerateImageAdminVO jobVO = buildAiArtGenerateImageVoForAdmin(po, jobResult,
					systemOptionService.encryptId(po.getId()));
			jobResultVoList.add(jobVO);
			jobResult = null;
		}

		AiUserDetailInRedisDTO userDetailDTO = null;
		Map<String, AiUserDetailInRedisDTO> userDetailInRedisMap = new HashMap<>();
		for (Long aiUserId : aiUserIdSet) {
			userDetailDTO = new AiUserDetailInRedisDTO();
			userDetailDTO.setUserId(aiUserId);
			userDetailDTO.setFreeJobCountingLastThreeDays(getFreeJobCountingOfLastThreeDays(aiUserId));
			userDetailDTO.setFreeJobCountingToday(getFreeJobCountingOfToday(aiUserId));
			userDetailDTO.setRechargeMarkThisWeek(getRechargeMarkThisWeek(aiUserId));
			userDetailInRedisMap.put(systemOptionService.encryptId(aiUserId), userDetailDTO);
		}

		r.setUserDetailInRedisMap(userDetailInRedisMap);

		r.setJobResultList(jobResultVoList);
		r.setIsSuccess();
		return r;
	}

	private CommonResult setImgInvalid(Long imgId) {
		imageService.setImageInvalidAndWaitingDelete(imgId);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult setInvalidImageAndRetunTokens(SetInvalidImageAndRetunTokensDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isAnyBlank(dto.getJobPk(), dto.getImgPk())) {
			r.setMessage("Null pk");
			return r;
		}

		Long imgId = systemOptionService.decryptPrivateKey(dto.getImgPk());
		Long jobId = systemOptionService.decryptPrivateKey(dto.getJobPk());
		if (imgId == null || jobId == null) {
			r.setMessage("Decrypt ID error");
			return r;
		}

		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);

		if (jobResult.isFail()) {
			r.setMessage(jobResult.getMessage());
			return r;
		}

		JSONObject parameterInJson = jobResult.getParameter();

		r = setImgInvalid(imgId);
		if (r.isFail()) {
			return r;
		}

		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		Long userId = jobPO.getAiUserId();

		List<ImgVO> imgVoList = jobResult.getImgVoList();
		boolean flag = false;
		for (int i = 0; i < imgVoList.size() && !flag; i++) {
			flag = imgVoList.get(i).getImgPk().equals(dto.getImgPk());
			if (flag) {
				ImgVO newImgVo = new ImgVO();
				newImgVo.setImgPk(aiArtOptionService.getImagePkInsteadOfNsfw());
				imgVoList.set(i, newImgVo);
			}
		}

		addNsfwJobCounting(userId);

		parameterInJson.put("jobId", jobId);
		saveAiArtGenerateImgResultJson(parameterInJson, imgVoList);

		if (!jobPO.getIsFreeJob()) {
			BigDecimal cost = null;
			Integer batcheSize = 0;
			if (parameterInJson.containsKey("init_images")) {
				TextToImageDTO parameterDTO = buildObjFromJsonCustomization(parameterInJson.toString(),
						TextToImageDTO.class);
				batcheSize = parameterDTO.getBatchSize();
				cost = calculateTokenCost(parameterDTO);
			} else {
				ImageToImageDTO parameterDTO = buildObjFromJsonCustomization(parameterInJson.toString(),
						ImageToImageDTO.class);
				batcheSize = parameterDTO.getBatchSize();
				cost = calculateTokenCost(parameterDTO);
			}
			BigDecimal returnTokens = cost.divide(new BigDecimal(batcheSize), RoundingMode.FLOOR)
					.multiply(new BigDecimal(0.98));
			aiChatUserService.recharge(userId, AiServiceAmountType.BONUS, returnTokens);
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult addToImageWall(AddToImageWallDTO dto) {
		CommonResult r = new CommonResult();
		Long imgId = systemOptionService.decryptPrivateKey(dto.getImgPk());
		if (imgId == null) {
			return r;
		}

		AiArtImageOnWallVO vo = new AiArtImageOnWallVO();
		vo.setJobPk(dto.getJobPk());
		vo.setImgPk(dto.getImgPk());
		vo.setJobId(systemOptionService.decryptPrivateKey(dto.getJobPk()));
		vo.setImgId(imgId);
		vo.setImgUrl(imageService.getImgThirdPartyUrlById(vo.getImgId()).getImgThirdPartyUrl());

		aiArtCacheService.getImageWall().add(vo);

		AiArtImageWall row = new AiArtImageWall();
		row.setImgId(vo.getImgId());
		row.setJobId(vo.getJobId());
		aiArtImageWallMapper.insertSelective(row);

		imageService.setImageValidTime(imgId, LocalDateTime.of(2999, 12, 31, 23, 59));

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult removeFromImageWall(String imgPk) {
		CommonResult r = new CommonResult();
		Long imgId = systemOptionService.decryptPrivateKey(imgPk);
		if (imgId == null) {
			return r;
		}

		List<AiArtImageOnWallVO> wall = aiArtCacheService.getImageWall();
		if (wall != null && !wall.isEmpty()) {
			for (int i = 0; i < wall.size(); i++) {
				if (wall.get(i).getImgId() == imgId) {
					wall.remove(i);
					i = wall.size();
				}
			}
		}

		aiArtImageWallMapper.deleteByPrimaryKey(imgId);
		imageService.setImageValidTime(imgId, LocalDateTime.now().plusMonths(1));

		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView getImageManagerView() {
		ModelAndView v = new ModelAndView("aiArtJSP/aiArtImageWallManager");
		AiArtImageWallResult wall = aiArtService.getImageWallFull(true);
		for (AiArtImageOnWallVO vo : wall.getImgVoList()) {
			if (vo.getJobId() == null || vo.getJobId() == 0) {
				vo.setJobId(systemOptionService.decryptPrivateKey(vo.getJobPk()));
			}
			if (vo.getImgId() == null || vo.getImgId() == 0) {
				vo.setImgId(systemOptionService.decryptPrivateKey(vo.getImgPk()));
			}
		}
		v.addObject("imgVoList", wall.getImgVoList());
		List<Long> imgIdList = new ArrayList<>();
		for (AiArtImageOnWallVO vo : wall.getImgVoList()) {
			imgIdList.add(vo.getImgId());
		}
		GetImgThirdPartyUrlInBatchResult urlResult = imageService.getImgThirdPartyUrlBatchResultById(imgIdList);
		if (urlResult.isFail()) {
			return v;
		}

		Map<String, String> urlMap = urlResult.getImgPkMatchUrl();
		for (AiArtImageOnWallVO vo : wall.getImgVoList()) {
			if (urlMap.containsKey(vo.getImgPk())) {
				vo.setImgUrl(urlMap.get(vo.getImgPk()));
			}
		}

		return v;
	}

	@Override
	public CommonResult setHadReview(String jobPk) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(jobPk)) {
			r.setMessage("PK error");
			return r;
		}
		Long jobId = systemOptionService.decryptPrivateKey(jobPk);

		return setHadReview(jobId);
	}

	@Override
	public CommonResult setHadReview(Long jobId) {
		CommonResult r = new CommonResult();
		if (jobId == null) {
			r.setMessage("Pk decrypt error");
			return r;
		}

		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		if (jobPO == null) {
			r.setMessage("PK error");
			return r;
		}
		jobPO.setHasReview(true);
		int updateCount = aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

		if (updateCount == 1) {
			r.setIsSuccess();
		}

		sendAiArtJobCompleteNoticeIfNecessary(jobPO.getAiUserId(), jobId);

		return r;
	}

	@Override
	public void setReviewBatchForAdmin() {
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		example.createCriteria().andAiUserIdEqualTo(aiCommonOptionService.getIdOfAdmin()).andHasReviewEqualTo(false);
		AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
		row.setHasReview(true);
		aiArtTextToImageJobRecordMapper.updateByExampleSelective(row, example);
	}

	@Override
	public SendTextToImgJobResult generateOtherLikeThat(BasePkDTO dto) {
		AiArtGenerateOtherLikeThatDTO dataDTO = new AiArtGenerateOtherLikeThatDTO();
		dataDTO.setJobPk(dto.getPk());
		return aiArtService.generateOtherLikeThat(dataDTO);
	}

	@Override
	public CommonResult regenerateImg(RegenerateImageDTO dto) {
		CommonResult r = new CommonResult();

		if (StringUtils.isBlank(dto.getJobPk())) {
			r.setMessage("key error");
			return r;
		}

		Long jobId = systemOptionService.decryptPrivateKey(dto.getJobPk());
		if (jobId == null) {
			r.setMessage("Job pk error");
			return r;
		}

		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);
		if (jobResult == null || jobResult.getParameter() == null) {
			r.setMessage("Job data error");
			return r;
		}

		JSONObject parameterInJson = jobResult.getParameter();
		if (parameterInJson.containsKey("init_images")) {
			r.setMessage("It's NOT a text to image job, can NOT regenerate an other by parameter");
			return r;
		}

		Long imgId = systemOptionService.decryptPrivateKey(dto.getImgPk());
		if (imgId == null || jobId == null) {
			r.setMessage("Decrypt ID error");
			return r;
		}

		List<ImgVO> imgVoList = jobResult.getImgVoList();
		boolean flag = false;
		for (int i = 0; i < imgVoList.size() && !flag; i++) {
			flag = imgVoList.get(i).getImgPk().equals(dto.getImgPk());
			if (flag) {
				ImgVO newImgVo = new ImgVO();
				newImgVo.setImgPk(aiArtOptionService.getImagePkInsteadOfNsfw());
				imgVoList.set(i, newImgVo);
			}
		}

		r = saveAiArtGenerateImgResultJson(parameterInJson, imgVoList);
		if (r.isFail()) {
			log.error("Can NOT update result json file, jobID: " + jobId);
			r.setMessage("Can NOT update result json file");
			return r;
		}

		r = setImgInvalid(imgId);
		if (r.isFail()) {
			log.error("Can NOT update image invalid, imgID: " + imgId);
			return r;
		}

		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		jobPO.setJobStatus(AiArtJobStatusType.WAITING.getCode().byteValue());
		jobPO.setRunCount(jobPO.getRunCount() - 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

		return r;
	}
}
