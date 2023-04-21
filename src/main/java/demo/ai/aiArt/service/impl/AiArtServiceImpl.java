package demo.ai.aiArt.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.TextToImageFromDTO;
import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.mq.producer.AiArtTextToImageProducer;
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
	private AiArtColabUtil aiArtColabUtil;

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

		return sendTextToImgDtoToMq(aiChatUserId, dto, false);
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

	private SendTextToImgJobResult sendTextToImgDtoToMq(Long aiUserId, TextToImageFromDTO dto, boolean isFromApi) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();

		Integer jobCounting = getJobCounting(aiUserId);
		if (jobCounting >= aiArtOptionService.getMaxDailyFreeJobCount()) {
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
		AiArtTextToImageJobRecord row = new AiArtTextToImageJobRecord();
		row.setAiUserId(aiUserId);
		row.setId(jobId);
		row.setJobStatus(AiArtJobStatusType.WAITING.getCode().byteValue());
		row.setIsFromApi(isFromApi);
		aiArtTextToImageJobRecordMapper.insertSelective(row);

		CommonResult saveParameterResult = saveTextToImgParameterDTO(dto);
		if (saveParameterResult.isFail()) {
			r.setMessage(saveParameterResult.getMessage());
			return r;
		}

		aiArtTextToImageProducer.send(jobId);
		r.setJobPk(systemOptionService.encryptId(jobId));
		r.setIsSuccess();

		return r;
	}

	private CommonResult saveTextToImgParameterDTO(TextToImageFromDTO dto) {
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
		AiArtTextToImageJobRecord po = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		if (po == null || AiArtJobStatusType.SUCCESS.getCode().byteValue() == po.getJobStatus()) {
			CommonResult r = new CommonResult();
			r.setMessage("Job had done");
			return r;
		}
		String parameterPathStr = getParameterPathByJobId(jobId);
		String content = fileUtilCustom.getStringFromFile(parameterPathStr);
		TextToImageFromDTO dto = buildObjFromJsonCustomization(content, TextToImageFromDTO.class);
		CommonResult txtToImgResult = null;
		if (systemOptionService.isDev() && !aiArtOptionService.getIsRunning()) {
			txtToImgResult = sendTxtToImgRequestWhenDev(dto);
		} else {
			txtToImgResult = sendTxtToImgRequest(dto);
		}

		if (txtToImgResult.isSuccess()) {
			AiArtGeneratingRecord imgGeneratingRecord = new AiArtGeneratingRecord();
			imgGeneratingRecord.setAiUserId(po.getAiUserId());
			imgGeneratingRecord.setId(dto.getJobId());
			imgGeneratingRecord.setTokens(calculateTokenCost(dto));
			aiArtGeneratingRecordMapper.insertSelective(imgGeneratingRecord);

			Integer jobCounting = getJobCounting(po.getAiUserId());
			if (jobCounting > aiArtOptionService.getMaxDailyFreeJobCount()) {
				aiChatUserService.__debitAmountAndAddTokenUsage(po.getAiUserId(), imgGeneratingRecord.getTokens());
			}
		}

		return txtToImgResult;
	}

	private CommonResult sendTxtToImgRequestWhenDev(TextToImageFromDTO dto) {
		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
		jobPO.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
		jobPO.setRunCount(jobPO.getRunCount() + 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

//		Need old DTO for save, bug send SFW DTO to generate images
		@SuppressWarnings("unused")
		TextToImageFromDTO dtoForSending = null;
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

		addJobCounting(jobPO.getAiUserId());

		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}

	private CommonResult sendTxtToImgRequest(TextToImageFromDTO dto) {
		CommonResult r = new CommonResult();

		// Need old DTO for save, bug send SFW DTO to generate images
		TextToImageFromDTO dtoForSending = null;
		if (!dto.getIsFromApi()) {
			dtoForSending = handleParamaterDTOFromWechat(dto);
		} else {
			dtoForSending = dto;
		}

		JSONObject responseJson = aiArtColabUtil.sendTxtToImgRequest(dtoForSending);
		if (responseJson == null || !responseJson.containsKey("images")) {
			AiArtTextToImageJobRecord row = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
			row.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
			row.setRunCount(row.getRunCount() + 1);
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(row);
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

		addJobCounting(jobPO.getAiUserId());

		r.setIsSuccess();
		return r;

	}

	private TextToImageFromDTO handleParamaterDTOFromWechat(TextToImageFromDTO dto) {
		TextToImageFromDTO newDTO = new TextToImageFromDTO();
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
			aiArtTextToImageProducer.send(po.getId());
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
				.andCreateTimeGreaterThan(LocalDateTime.now().minusDays(3));
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
	public List<AiArtTextToImageJobRecord> __getJobResultPage(String lastJobPk) {
		Long lastjobId = systemOptionService.decryptPrivateKey(lastJobPk);
		RowBounds rowBounds = new RowBounds(0, 20);
		AiArtTextToImageJobRecordExample example = new AiArtTextToImageJobRecordExample();
		Criteria criteria = example.createCriteria();
		if (lastjobId != null) {
			criteria.andIdLessThan(lastjobId);
		}
		example.setOrderByClause("id desc");
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
			AiArtImageWallResult dto = buildObjFromJsonCustomization(content, AiArtImageWallResult.class);
			if (dto != null) {
				aiArtCacheService.setImageWall(dto);
			}
		} catch (Exception e) {
			sendTelegramMessage("Load image wall DTO to cache failed: " + e.getLocalizedMessage());
		}
	}

	@Override
	public AiArtImageWallResult getImageWall() {
		return getImageWall(false);
	}

	@Override
	public AiArtImageWallResult getImageWall(Boolean refresh) {
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

		TextToImageFromDTO oldParameter = jobResult.getParameter();
		oldParameter.setBatchSize(1);

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
		}
		return r;
	}
}
