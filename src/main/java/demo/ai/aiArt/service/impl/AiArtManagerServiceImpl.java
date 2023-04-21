package demo.ai.aiArt.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ai.aiArt.pojo.dto.AiArtImageWallDTO;
import ai.aiArt.pojo.dto.TextToImageFromDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import ai.aiArt.pojo.vo.AiArtImageOnWallVO;
import ai.aiChat.pojo.type.AiChatAmountType;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.pojo.dto.SetInvalidImageAndRetunTokensDTO;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.aiArt.service.AiArtManagerService;
import demo.ai.aiArt.service.AiArtService;
import demo.image.service.ImageService;

@Service
public class AiArtManagerServiceImpl extends AiArtCommonService implements AiArtManagerService {

	@Autowired
	private AiArtService aiArtService;
	@Autowired
	private ImageService imageService;

	@Override
	public ModelAndView getManagerView() {
		ModelAndView v = new ModelAndView("aiArtJSP/aiArtManager");
		v.addObject("isRunning", aiArtOptionService.getIsRunning());
		return v;
	}

	@Override
	public CommonResult setRunningColab(BaseStrDTO dto) {
		if (dto == null || StringUtils.isBlank(dto.getStr())) {
			return new CommonResult();
		}
		aiArtOptionService.setMainUrl(dto.getStr());
		aiArtOptionService.setIsRunning(true);

		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult setStopColab() {
		aiArtOptionService.setMainUrl(null);
		aiArtOptionService.setIsRunning(false);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	@Override
	public GetJobResultList getAiArtJobList(BasePkDTO dto) {
		GetJobResultList r = new GetJobResultList();
		List<AiArtTextToImageJobRecord> jobPoList = aiArtService.__getJobResultPage(dto.getPk());
		if (jobPoList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}
		AiArtGenerateImageResult jobResult = null;
		List<AiArtGenerateImageVO> voList = new ArrayList<>();
		for (AiArtTextToImageJobRecord po : jobPoList) {
			if (AiArtJobStatusType.SUCCESS.getCode().equals(po.getJobStatus().intValue())) {
				jobResult = getJobResult(po.getId());
			}
			voList.add(buildAiArtGenerateImageVO(po, jobResult, systemOptionService.encryptId(po.getId())));
			jobResult = null;
		}
		r.setJobResultList(voList);
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

		GetJobResultList jobResultList = getJobResultVoByJobPk(dto.getJobPk());
		if (jobResultList.isFail()) {
			r.setMessage(jobResultList.getMessage());
			return r;
		}

		if (jobResultList.getJobResultList().isEmpty()
				|| jobResultList.getJobResultList().get(0).getParameter() == null) {
			r.setMessage("Job result data error");
			return r;
		}

		AiArtGenerateImageVO jobResultVO = jobResultList.getJobResultList().get(0);
		TextToImageFromDTO parameter = jobResultVO.getParameter();

		Long imgId = systemOptionService.decryptPrivateKey(dto.getImgPk());
		Long jobId = systemOptionService.decryptPrivateKey(dto.getJobPk());
		Long userId = systemOptionService.decryptPrivateKey(jobResultVO.getAiUserPk());
		if (imgId == null || jobId == null || userId == null) {
			r.setMessage("Decrypt ID error");
			return r;
		}

		r = setImgInvalid(imgId);
		if (r.isFail()) {
			return r;
		}

		List<String> imgPkList = jobResultVO.getImgPkList();
		boolean flag = false;
		for (int i = 0; i < imgPkList.size() && !flag; i++) {
			flag = imgPkList.get(i).equals(dto.getImgPk());
			if (flag) {
				imgPkList.set(i, aiArtOptionService.getImagePkInsteadOfNsfw());
			}
		}

		parameter.setJobId(jobId);
		saveAiArtGenerateImgResultJson(parameter, imgPkList);

		BigDecimal totalTokens = calculateTokenCost(parameter);

		BigDecimal returnTokens = totalTokens.divide(new BigDecimal(parameter.getBatchSize()), RoundingMode.FLOOR)
				.multiply(new BigDecimal(0.95));

		aiChatUserService.recharge(userId, AiChatAmountType.BONUS, returnTokens);

		r.setIsSuccess();
		return r;
	}

	@Override
	public void addToImageWall(String jobPk, String imgPk) {
		AiArtImageOnWallVO vo = new AiArtImageOnWallVO();
		vo.setJobPk(jobPk);
		vo.setImgPk(imgPk);
		AiArtImageWallDTO dto = aiArtCacheService.getImageWall();
		if (dto == null) {
			dto = new AiArtImageWallDTO();
		}
		List<AiArtImageOnWallVO> voList = dto.getImgVoList();
		if (voList == null) {
			voList = new ArrayList<>();
		}
		voList.add(0, vo);
		while (voList.size() > aiArtOptionService.getImageWallMaxSize()) {
			voList.remove(voList.size() - 1);
		}

		dto.setImgVoList(voList);
		aiArtCacheService.setImageWall(dto);

		Long imgId = systemOptionService.decryptPrivateKey(imgPk);
		if (imgId != null) {
			imageService.setImageValidTime(imgId, LocalDateTime.of(2999, 12, 31, 23, 59));
		}
	}

	@Override
	public CommonResult removeFromImageWall(String imgPk) {
		CommonResult r = new CommonResult();
		AiArtImageWallDTO dto = aiArtCacheService.getImageWall();
		if (dto == null) {
			dto = new AiArtImageWallDTO();
			aiArtCacheService.setImageWall(dto);
			r.setIsSuccess();
			return r;
		}
		List<AiArtImageOnWallVO> voList = dto.getImgVoList();
		if (voList == null || voList.isEmpty()) {
			dto.setImgVoList(new ArrayList<>());
			aiArtCacheService.setImageWall(dto);
			r.setIsSuccess();
			return r;
		}

		boolean matchFlag = false;
		for (int i = 0; i < voList.size() && !matchFlag; i++) {
			matchFlag = voList.get(i).getImgPk().equals(imgPk);
			if (matchFlag) {
				voList.remove(i);
			}
		}

		if (matchFlag) {
			Long imgId = systemOptionService.decryptPrivateKey(imgPk);
			if (imgId != null) {
				imageService.setImageValidTime(imgId, LocalDateTime.now().plusMonths(1));
			}
		}

		r.setIsSuccess();
		return r;
	}

}
