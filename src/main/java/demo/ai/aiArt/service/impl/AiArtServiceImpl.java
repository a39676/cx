package demo.ai.aiArt.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.TextToImageDTO;
import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.type.AiArtSamplerType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import ai.aiArt.pojo.vo.AiArtImageOnWallVO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.mq.producer.AiArtTextToImageProducer;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.pojo.po.AiArtGeneratingRecord;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecordExample;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecordExample.Criteria;
import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.aiArt.service.AiArtService;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.common.pojo.dto.BaseDTO;
import demo.image.pojo.type.ImageTagType;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class AiArtServiceImpl extends AiArtCommonService implements AiArtService {

	@Autowired
	private AiArtAutomaticUtil aiArtColabUtil;

	@Autowired
	private AiArtTextToImageProducer aiArtTextToImageProducer;
	@Autowired
	private FileUtilCustom fileUtilCustom;

	@Override
	public SendTextToImgJobResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();
		if (StringUtils.isBlank(dto.getTmpKey())) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(dto.getTmpKey());
		if (aiChatUserId == null) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		AiChatUserDetail aiUser = aiChatUserService.__getUserDetail(aiChatUserId);
		if (aiUser == null || aiUser.getIsBlock() || aiUser.getIsDelete()) {
			r.setMessage("暂时无法为您新增绘图任务, 请返回微信给管理员留言; 请勿频繁生成违规内容, 谢谢!");
			return r;
		}

		extendTmpKeyValidity(Long.parseLong(dto.getTmpKey()));
		SendTextToImgJobResult addJobResult = sendTextToImgDtoToMq(aiChatUserId, dto, false);

		if (dto.getNoticeWhenComplete() != null && dto.getNoticeWhenComplete() && addJobResult.isSuccess()) {
			addNoticeWhenCompleteMark(aiChatUserId, systemOptionService.decryptPrivateKey(addJobResult.getJobPk()));
		}
		return addJobResult;
	}

	@Override
	public SendTextToImgJobResult sendTextToImgFromApiDtoToMq(TextToImageFromApiDTO dto) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();
		if (StringUtils.isBlank(dto.getApiKey())) {
			r.setMessage("请输入正确 API key");
			return r;
		}

		Long aiChatUserId = getAiUserIdByApiKey(dto.getApiKey());
		if (aiChatUserId == null) {
			r.setMessage("请输入正确 API key");
			return r;
		}

		return sendTextToImgDtoToMq(aiChatUserId, dto, true);
	}

	private SendTextToImgJobResult sendTextToImgDtoToMq(Long aiUserId, TextToImageDTO dto, boolean isFromApi) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();

		Integer jobCounting = getJobCounting(aiUserId);
		boolean isFreeJobFlag = (jobCounting <= aiArtOptionService.getMaxDailyFreeJobCount());
		if (!isFreeJobFlag) {
			AiChatUserDetail userDetail = aiChatUserService.__getUserDetail(aiUserId);
			int totalAmount = userDetail.getBonusAmount().intValue() + userDetail.getRechargeAmount().intValue();
			if (totalAmount <= 0) {
				r.setMessage("余额不足, 请到个人中心购买充值包 签到, 或留意其他活动");
				return r;
			}
		}

		if (StringUtils.isBlank(dto.getPrompts())) {
			r.setMessage("请输入 prompts");
			return r;
		}

		if (dto.getPrompts().length() + dto.getNegativePrompts().length() > aiArtOptionService.getMaxPromptLength()) {
			r.setMessage("Too much prompts, prompts + nagative prompts should less than "
					+ aiArtOptionService.getMaxPromptLength());
			return r;
		}

		if (dto.getWidth() != null && dto.getWidth() > 0) {
			if (dto.getWidth() > aiArtOptionService.getMaxWidth()) {
				dto.setWidth(aiArtOptionService.getMaxWidth());
			}
		}
		if (dto.getHeight() != null && dto.getHeight() > 0) {
			if (dto.getHeight() > aiArtOptionService.getMaxHeight()) {
				dto.setHeight(aiArtOptionService.getMaxHeight());
			}
		}
		if (dto.getCfgScale() != null && dto.getCfgScale() > 0) {
			if (dto.getCfgScale() > aiArtOptionService.getMaxCfgScale()) {
				dto.setCfgScale(aiArtOptionService.getMaxCfgScale());
			}
		}
		if (dto.getSteps() != null && dto.getSteps() > 0) {
			if (dto.getSteps() > aiArtOptionService.getMaxSteps()) {
				dto.setSteps(aiArtOptionService.getMaxSteps());
			}
		}
		if (dto.getBatchSize() != null) {
			if (dto.getBatchSize() > aiArtOptionService.getMaxBatch()) {
				dto.setBatchSize(aiArtOptionService.getMaxBatch());
			}
			if (dto.getBatchSize() < 0) {
				dto.setBatchSize(1);
			}
		}

		AiArtSamplerType samplerType = AiArtSamplerType.getType(dto.getSampler());
		if (samplerType == null) {
			samplerType = AiArtSamplerType.Euler_A;
			dto.setSampler(samplerType.getName());
		}

		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		Criteria waiting = example.createCriteria();
		waiting.andAiUserIdEqualTo(aiUserId).andJobStatusEqualTo(AiArtJobStatusType.WAITING.getCode().byteValue());
		Criteria failButWillRerun = example.createCriteria();
		failButWillRerun.andAiUserIdEqualTo(aiUserId)
				.andJobStatusEqualTo(AiArtJobStatusType.FAILED.getCode().byteValue())
				.andRunCountLessThan(aiArtOptionService.getMaxFailCountForJob());
		example.or(failButWillRerun);
		List<AiArtTextToImageJobRecord> waitingJobList = aiArtTextToImageJobRecordMapper.selectByExample(example);
		if (waitingJobList.size() > aiArtOptionService.getMaxWaitingJobCount()) {
			r.setMessage("You have too many waiting jobs, please insert later");
			return r;
		}

		Long jobId = snowFlake.getNextId();
		dto.setJobId(jobId);
		dto.setIsFromApi(isFromApi);
		AiArtTextToImageJobRecord newJobPO = new AiArtTextToImageJobRecord();
		newJobPO.setId(jobId);
		newJobPO.setIsFreeJob(isFreeJobFlag);
		newJobPO.setAiUserId(aiUserId);
		newJobPO.setJobStatus(AiArtJobStatusType.WAITING.getCode().byteValue());
		newJobPO.setIsFromApi(isFromApi);
		aiArtTextToImageJobRecordMapper.insertSelective(newJobPO);

		CommonResult saveParameterResult = saveTextToImgParameterDTO(dto);
		if (saveParameterResult.isFail()) {
			r.setMessage(saveParameterResult.getMessage());
			return r;
		}

		addJobInQueueMark(jobId);
		aiArtTextToImageProducer.send(jobId);

		if (!aiArtOptionService.getIdOfAdmin().equals(aiUserId)) {
			sendTelegramMessage("New AI art job recive, ready for review please");
		}

		addJobCounting(aiUserId);
		r.setJobPk(systemOptionService.encryptId(jobId));
		r.setIsFreeJob(isFreeJobFlag);
		if (isFreeJobFlag) {
			r.setFreeJobCountLeft(aiArtOptionService.getMaxDailyFreeJobCount() - jobCounting);
		} else {
			r.setFreeJobCountLeft(0);
		}

		r.setIsRunning(aiArtOptionService.getIsRunning());
		r.setIsSuccess();
		return r;
	}

	private CommonResult saveTextToImgParameterDTO(TextToImageDTO dto) {
		CommonResult r = new CommonResult();
		String folderPathStr = aiArtOptionService.getTextToImageParameterSavingFolder();
		File mainFolder = new File(folderPathStr);
		if (!mainFolder.exists()) {
			if (!mainFolder.mkdirs()) {
				r.setMessage("Can NOT create parameter saving folder");
				return r;
			}
		}

		JSONObject settingsJson = JSONObject.fromObject(dto);

		File outputSettings = new File(folderPathStr + File.separator + dto.getJobId() + ".json");

		try {
			FileUtils.writeByteArrayToFile(outputSettings, settingsJson.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			r.setMessage("Text to image parameter cache error: " + e.getLocalizedMessage());
			e.printStackTrace();
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	private String getParameterPathByJobId(Long jobId) {
		File outputSettings = new File(
				aiArtOptionService.getTextToImageParameterSavingFolder() + File.separator + jobId + ".json");
		if (!outputSettings.exists()) {
			return null;
		}
		return outputSettings.getAbsolutePath();
	}

	@Override
	public CommonResult txtToImgByJobId(Long jobId) {
		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		CommonResult r = new CommonResult();
		if (jobPO == null || AiArtJobStatusType.SUCCESS.getCode().byteValue() == jobPO.getJobStatus()) {
			r.setMessage("Job had done");
			return r;
		}
		String parameterPathStr = getParameterPathByJobId(jobId);
		String content = fileUtilCustom.getStringFromFile(parameterPathStr);
		TextToImageDTO parameterDTO = buildObjFromJsonCustomization(content, TextToImageDTO.class);

		Long aiUserId = jobPO.getAiUserId();
		AiChatUserDetail userDetail = aiChatUserService.__getUserDetail(aiUserId);
		int totalAmount = userDetail.getBonusAmount().intValue() + userDetail.getRechargeAmount().intValue();
		if (totalAmount <= 0) {
			r.setMessage("余额不足, 请到个人中心购买充值包 签到, 或留意其他活动");
			jobPO.setRunCount(aiArtOptionService.getMaxFailCountForJob());
			jobPO.setJobStatus(AiArtJobStatusType.FAILED_BY_AMOUNT_NOT_ENOUGH.getCode().byteValue());
			jobPO.setHasReview(true);
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);
			minusJobCounting(jobPO.getAiUserId());
			return r;
		}

		CommonResult txtToImgResult = null;
		if (systemOptionService.isDev() && !aiArtOptionService.getIsRunning()) {
			txtToImgResult = sendTxtToImgRequestWhenDev(parameterDTO);
		} else {
			txtToImgResult = sendTxtToImgRequest(parameterDTO);
		}

		if (txtToImgResult.isSuccess()) {
			AiArtGeneratingRecord imgGeneratingRecord = new AiArtGeneratingRecord();
			imgGeneratingRecord.setAiUserId(jobPO.getAiUserId());
			imgGeneratingRecord.setId(parameterDTO.getJobId());
			imgGeneratingRecord.setTokens(calculateTokenCost(parameterDTO));
			aiArtGeneratingRecordMapper.insertSelective(imgGeneratingRecord);

			Integer jobCounting = getJobCounting(jobPO.getAiUserId());
			if (jobCounting > aiArtOptionService.getMaxDailyFreeJobCount()) {
				aiChatUserService.__debitAmountAndAddTokenUsage(jobPO.getAiUserId(), imgGeneratingRecord.getTokens());
			}
			removeJobInQueueMark(jobId);

		} else {
			if (jobPO.getRunCount() + 1 == aiArtOptionService.getMaxFailCountForJob()) {
				jobPO.setRunCount(jobPO.getRunCount() + 1);
				jobPO.setHasReview(true);
				aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);
				minusJobCounting(jobPO.getAiUserId());
			}
		}

		return txtToImgResult;
	}

	private CommonResult sendTxtToImgRequestWhenDev(TextToImageDTO dto) {
		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
		jobPO.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
		jobPO.setRunCount(jobPO.getRunCount() + 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

//		Need old DTO for save, bug send SFW DTO to generate images
		@SuppressWarnings("unused")
		TextToImageDTO dtoForSending = null;
		if (!dto.getIsFromApi()) {
			dtoForSending = handleParamaterDTOFromWechat(dto);
		} else {
			dtoForSending = dto;
		}

		List<String> imagePkList = new ArrayList<>();
		for (int i = 0; i < dto.getBatchSize(); i++) {
			imagePkList.add("gw/wq6+gbZ2IZkn1CJ+dm7+wwR5sL+ta0qgCzZ1YJIw=");
		}
		saveAiArtGenerateImgResultJson(dto, imagePkList);

		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	private CommonResult sendTxtToImgRequest(TextToImageDTO dto) {
		CommonResult r = new CommonResult();

		// Need old DTO for save, bug send SFW DTO to generate images
		TextToImageDTO dtoForSending = null;
		if (!dto.getIsFromApi()) {
			dtoForSending = handleParamaterDTOFromWechat(dto);
		} else {
			dtoForSending = dto;
		}

		JSONObject responseJson = aiArtColabUtil.sendTxtToImgRequest(dtoForSending);
		if (responseJson == null || !responseJson.containsKey("images")) {
			AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
			jobPO.setId(dto.getJobId());
			jobPO.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
			jobPO.setRunCount(jobPO.getRunCount() + 1);
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);
			r.setMessage("Can NOT create image, please try again later");
			return r;
		}

		JSONArray imageListInBase64 = responseJson.getJSONArray("images");

		List<String> imagePkList = new ArrayList<>();
		for (int i = 0; i < imageListInBase64.size(); i++) {
			ImageSavingResult imgSavingResult = saveImg(imageListInBase64.getString(i));
			if (imgSavingResult.isFail()) {
				AiArtTextToImageJobRecord row = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
				row.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
				row.setRunCount(row.getRunCount() + 1);
				aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
				r.setMessage("Can NOT create image, please try again later");
				return r;
			}
			imagePkList.add(imgSavingResult.getImgPK());
		}

		r = saveAiArtGenerateImgResultJson(dto, imagePkList);

		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
		jobPO.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
		jobPO.setRunCount(jobPO.getRunCount() + 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

		r.setIsSuccess();
		return r;

	}

	private TextToImageDTO handleParamaterDTOFromWechat(TextToImageDTO dto) {
		TextToImageDTO newDTO = new TextToImageDTO();
		BeanUtils.copyProperties(dto, newDTO);

		String filterPrompts = promptsNsfwFilter(dto.getPrompts());
		newDTO.setPrompts(filterPrompts);
		StringBuffer sb = new StringBuffer(dto.getNegativePrompts());
		sb.append(",");
		for (String prompt : aiArtOptionService.getNsfwPrompt()) {
			sb.append(prompt + ",");
		}
		newDTO.setNegativePrompts(sb.toString());
		return newDTO;
	}

	private String promptsNsfwFilter(String prompts) {
		String[] promptArray = prompts.split(",");
		List<String> promptList = new ArrayList<>();
		for (String prompt : promptArray) {
			promptList.add(prompt);
		}

		List<String> resultPromptList = new ArrayList<>();

		List<String> nsfwPromptList = aiArtOptionService.getNsfwPrompt();

		String sourcePrompt = null;
		String nsfwPrompt = null;
		boolean nsfwFlag = false;
		for (int i = 0; i < promptList.size(); i++) {
			sourcePrompt = promptList.get(i);
			for (int j = 0; j < nsfwPromptList.size() && !nsfwFlag; j++) {
				nsfwPrompt = nsfwPromptList.get(j);
				nsfwFlag = sourcePrompt.contains(nsfwPrompt);
			}
			if (!nsfwFlag) {
				resultPromptList.add(sourcePrompt);
			}
			nsfwFlag = false;
		}

		StringBuffer sb = new StringBuffer();
		for (String prompt : resultPromptList) {
			sb.append(prompt + ",");
		}
		return sb.toString();
	}

	private ImageSavingResult saveImg(String data) {
		ImageSavingTransDTO imgSavingDTO = new ImageSavingTransDTO();
		imgSavingDTO.setImgBase64Str(data);
		imgSavingDTO.setImgName(snowFlake.getNextId() + ".jpg");
		imgSavingDTO.setImgTagCode(ImageTagType.AI_ART.getCode());
		imgSavingDTO.setValidTime(LocalDateTime.now().plusDays(aiArtOptionService.getMaxJobLivingDay()));
		ImageSavingResult imgSavingResult = imageService.imageSaving(imgSavingDTO);

		if (imgSavingResult.isFail()) {
			sendTelegramMessage("AI生成图片异常, 无法保存图片: " + imgSavingResult.getMessage());
		}

		return imgSavingResult;
	}

	@Override
	public void rerun() {
		if (!aiArtOptionService.getIsRunning()) {
			return;
		}
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		example.createCriteria()
				.andJobStatusIn(Arrays.asList(AiArtJobStatusType.WAITING.getCode().byteValue(),
						AiArtJobStatusType.FAILED.getCode().byteValue()))
				.andIsDeleteEqualTo(false).andRunCountLessThan(aiArtOptionService.getMaxFailCountForJob());

		List<AiArtTextToImageJobRecord> poList = aiArtTextToImageJobRecordMapper.selectByExample(example);
		if (poList.isEmpty()) {
			return;
		}
		for (AiArtTextToImageJobRecord po : poList) {
			if (!isJobInQueue(po.getId())) {
				addJobInQueueMark(po.getId());
				aiArtTextToImageProducer.send(po.getId());
			}
		}
	}

	@Override
	public GetJobResultList getJobResultListByTmpKey(String userTmpKey) {
		GetJobResultList r = new GetJobResultList();
		if (StringUtils.isBlank(userTmpKey)) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(userTmpKey);
		if (aiChatUserId == null) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime minCreateTime = now.minusDays(aiArtOptionService.getMaxJobLivingDay());
		example.createCriteria().andAiUserIdEqualTo(aiChatUserId).andCreateTimeGreaterThan(minCreateTime)
				.andIsDeleteEqualTo(false).andIsFromApiEqualTo(false);
		example.setOrderByClause("create_time desc");
		RowBounds rowBounds = new RowBounds(0, aiArtOptionService.getMaxShowJob());
		List<AiArtTextToImageJobRecord> jobResultPoList = aiArtTextToImageJobRecordMapper
				.selectByExampleWithRowbounds(example, rowBounds);
		List<AiArtGenerateImageVO> voList = new ArrayList<>();
		AiArtGenerateImageVO vo = null;
		AiArtGenerateImageResult jobResult = null;
		for (AiArtTextToImageJobRecord po : jobResultPoList) {
			jobResult = getJobResult(po.getId());
			vo = buildAiArtGenerateImageVO(po, jobResult, systemOptionService.encryptId(po.getId()));
			voList.add(vo);
		}
		r.setJobResultList(voList);
		r.setIsSuccess();

		extendTmpKeyValidity(Long.parseLong(userTmpKey));
		return r;
	}

	@Override
	public GetJobResultList getJobResultVoByJobPk(BaseDTO dto) {
		return getJobResultVoByJobPk(dto.getPk());
	}

	@Override
	public void deleteParameterFile() {
		Set<Long> targetIdSet = new HashSet<>();
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		example.createCriteria().andJobStatusEqualTo(AiArtJobStatusType.SUCCESS.getCode().byteValue())
				.andHasReviewEqualTo(true).andCreateTimeGreaterThan(LocalDateTime.now().minusDays(3));
		List<AiArtTextToImageJobRecord> list = aiArtTextToImageJobRecordMapper.selectByExample(example);
		for (AiArtTextToImageJobRecord po : list) {
			targetIdSet.add(po.getId());
		}
		example.createCriteria().andRunCountGreaterThanOrEqualTo(aiArtOptionService.getMaxFailCountForJob())
				.andCreateTimeGreaterThan(LocalDateTime.now().minusDays(3));
		list = aiArtTextToImageJobRecordMapper.selectByExample(example);
		for (AiArtTextToImageJobRecord po : list) {
			targetIdSet.add(po.getId());
		}

		if (targetIdSet.isEmpty()) {
			return;
		}

		String resultJsonSavePath = null;
		File file = null;
		for (Long id : targetIdSet) {
			resultJsonSavePath = getJobResultStrPath(id);
			file = new File(resultJsonSavePath);
			if (file.exists()) {
				file.deleteOnExit();
			}
		}
	}

	@Override
	public List<AiArtTextToImageJobRecord> getJobResultPageForWechatUser(String lastJobPk, String tmpKey) {
		AiArtJobListFilterDTO dto = new AiArtJobListFilterDTO();
		Long aiUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiUserId == null) {
			return new ArrayList<>();
		}
		dto.setLastJobPk(lastJobPk);
		return getJobResultPage(dto, aiUserId);
	}

	@Override
	public List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto) {
		return getJobResultPage(dto, null);
	}

	@Override
	public List<AiArtTextToImageJobRecord> getJobResultPage(AiArtJobListFilterDTO dto, Long aiUserId) {
		Long lastjobId = systemOptionService.decryptPrivateKey(dto.getLastJobPk());
		if (!isBigUser()) {
			dto.setHasReview(null);
			dto.setIsFromApi(null);
			dto.setOrderBy(null);
		}
		RowBounds rowBounds = new RowBounds(0, aiArtOptionService.getMaxShowJob());
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		Criteria criteria = example.createCriteria();
		if (aiUserId != null) {
			criteria.andAiUserIdEqualTo(aiUserId);
		}
		if (lastjobId != null) {
			criteria.andIdLessThan(lastjobId);
		}
		if (dto.getHasReview() != null) {
			criteria.andHasReviewEqualTo(dto.getHasReview());
		}
		if (dto.getIsFreeJob() != null) {
			criteria.andIsFreeJobEqualTo(dto.getIsFreeJob());
		}
		if (dto.getJobStatus() != null) {
			criteria.andJobStatusEqualTo(dto.getJobStatus().byteValue());
		}
		if (StringUtils.isNotBlank(dto.getCreateTimeStartStr())) {
			criteria.andCreateTimeGreaterThan(
					localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getCreateTimeStartStr()));
		}
		if (StringUtils.isNotBlank(dto.getCreateTimeEndStr())) {
			criteria.andCreateTimeLessThan(
					localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(dto.getCreateTimeEndStr()));
		}
		if (dto.getIsFromApi() != null) {
			criteria.andIsFromApiEqualTo(dto.getIsFromApi());
		}

		if (StringUtils.isNotBlank(dto.getOrderBy())) {
			example.setOrderByClause(dto.getOrderBy());
		} else {
			example.setOrderByClause("id desc");
		}
		List<AiArtTextToImageJobRecord> poList = aiArtTextToImageJobRecordMapper.selectByExampleWithRowbounds(example,
				rowBounds);
		return poList;
	}

	@Override
	public void refreshImageWallJsonFile() {
		AiArtImageWallResult dto = aiArtCacheService.getImageWall();
		JSONObject json = JSONObject.fromObject(dto);
		fileUtilCustom.byteToFile(json.toString(), aiArtOptionService.getImageWallFilePath());
	}

	@Override
	public void loadImageWallToCache() {
		try {
			String content = fileUtilCustom.getStringFromFile(aiArtOptionService.getImageWallFilePath());
			AiArtImageWallResult dto = null;
			if (content == null || StringUtils.isBlank(content)) {
				dto = new AiArtImageWallResult();
			} else {
				dto = buildObjFromJsonCustomization(content, AiArtImageWallResult.class);
			}
			if (dto.getImgVoList() == null) {
				dto.setImgVoList(new ArrayList<>());
			}
			aiArtCacheService.setImageWall(dto);
		} catch (Exception e) {
			sendTelegramMessage("Load image wall DTO to cache failed: " + e.getLocalizedMessage());
		}
	}

	@Override
	public AiArtImageWallResult getImageWallRandomSub() {
		AiArtImageWallResult fullResult = getImageWallFull(null);
		List<AiArtImageOnWallVO> voList = fullResult.getImgVoList();
		if (fullResult.getImgVoList().isEmpty()
				&& aiArtOptionService.getImageWallOnShowMaxSize() > fullResult.getImgVoList().size()) {
			return fullResult;
		}

		List<AiArtImageOnWallVO> resultVoList = new ArrayList<>();
		Random random = new Random();
		Integer randomIndex = null;
		for (int i = 0; i < aiArtOptionService.getImageWallOnShowMaxSize() && !voList.isEmpty(); i++) {
			randomIndex = random.nextInt(voList.size());
			resultVoList.add(voList.get(randomIndex));
			voList.remove(randomIndex.intValue());
		}

		fullResult.setImgVoList(resultVoList);
		return fullResult;
	}

	@Override
	public AiArtImageWallResult getImageWallFull(Boolean refresh) {
		if (refresh != null && refresh) {
			loadImageWallToCache();
		}
		AiArtImageWallResult dto = aiArtCacheService.getImageWall();
		if (dto != null) {
			return dto;
		}
		loadImageWallToCache();
		return aiArtCacheService.getImageWall();
	}

	@Override
	public SendTextToImgJobResult generateOtherLikeThat(AiArtGenerateOtherLikeThatDTO dto) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();

		if (StringUtils.isBlank(dto.getJobPk())) {
			r.setMessage("key error");
			return r;
		}

		Long jobId = systemOptionService.decryptPrivateKey(dto.getJobPk());
		if (jobId == null) {
			r.setMessage("Job pk error");
			return r;
		}

		AiArtGenerateImageResult jobResult = getJobResult(jobId);
		if (jobResult == null || jobResult.getParameter() == null) {
			r.setMessage("Job data error");
			return r;
		}

		TextToImageDTO oldParameter = jobResult.getParameter();
		oldParameter.setBatchSize(2);
		// Generate two images, in case of a terrible output, there will be an other
		// better one

		if (isBigUser()) {
			TextToImageFromApiDTO paramDTO = new TextToImageFromApiDTO();
			BeanUtils.copyProperties(oldParameter, paramDTO);
			paramDTO.setApiKey(aiArtOptionService.getApiKeyOfAdmin());
			r = sendTextToImgFromApiDtoToMq(paramDTO);

		} else {
			TextToImageFromWechatDTO paramDTO = new TextToImageFromWechatDTO();
			BeanUtils.copyProperties(oldParameter, paramDTO);
			paramDTO.setTmpKey(dto.getTmpKey());
			r = sendTextToImgFromWechatDtoToMq(paramDTO);
			extendTmpKeyValidity(Long.parseLong(dto.getTmpKey()));
		}

		return r;
	}

	@Override
	public void __sendRandomGenerateJob() {
		AiArtImageWallResult imageWall = getImageWallRandomSub();
		if (imageWall.getImgVoList().isEmpty()) {
			return;
		}

		List<AiArtImageOnWallVO> voList = imageWall.getImgVoList();
		Random random = new Random();
		int randomInt = random.nextInt(voList.size());

		AiArtImageOnWallVO vo = voList.get(randomInt);

		Long jobId = systemOptionService.decryptPrivateKey(vo.getJobPk());
		if (jobId == null) {
			return;
		}

		AiArtGenerateImageResult jobResult = getJobResult(jobId);
		if (jobResult == null || jobResult.getParameter() == null) {
			return;
		}

		TextToImageDTO oldParameter = jobResult.getParameter();
		oldParameter.setBatchSize(4);

		TextToImageFromApiDTO paramDTO = new TextToImageFromApiDTO();
		BeanUtils.copyProperties(oldParameter, paramDTO);
		paramDTO.setApiKey(aiArtOptionService.getApiKeyOfAdmin());
		sendTextToImgFromApiDtoToMq(paramDTO);
	}

	@Override
	public void sendNoticeIfAnyJobsWaitingForReview() {
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		example.createCriteria().andJobStatusEqualTo(AiArtJobStatusType.SUCCESS.getCode().byteValue())
				.andHasReviewEqualTo(false).andIsFromApiEqualTo(false);
		List<AiArtTextToImageJobRecord> list = aiArtTextToImageJobRecordMapper.selectByExample(example);
		if (!list.isEmpty()) {
			sendTelegramMessage(list.size() + " jobs waiting for review");
		}
	}

}
