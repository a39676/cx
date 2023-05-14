package demo.ai.aiArt.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiArt.pojo.dto.TextToImageDTO;
import ai.aiArt.pojo.dto.TextToImageFromWechatDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageQueryResult;
import ai.aiArt.pojo.result.AiArtImageWallResult;
import ai.aiArt.pojo.result.AiArtTxtToImgResult;
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.result.SendTextToImgJobResult;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.type.AiArtSamplerType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import ai.aiArt.pojo.vo.AiArtImageOnWallVO;
import ai.aiChat.pojo.type.AiServiceAmountType;
import ai.automatic1111.pojo.type.AiArtDefaultModelType;
import ai.automatic1111.pojo.type.AiArtUpscalerType;
import ai.pojo.vo.AiArtModelVO;
import auxiliaryCommon.pojo.dto.BasePkDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.mapper.AiArtModelMapper;
import demo.ai.aiArt.mq.producer.AiArtTextToImageProducer;
import demo.ai.aiArt.pojo.dto.AiArtJobListFilterDTO;
import demo.ai.aiArt.pojo.dto.TextToImageFromApiDTO;
import demo.ai.aiArt.pojo.po.AiArtGeneratingRecord;
import demo.ai.aiArt.pojo.po.AiArtModel;
import demo.ai.aiArt.pojo.po.AiArtModelExample;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecordExample;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecordExample.Criteria;
import demo.ai.aiArt.pojo.result.GetAiArtAllModelListResult;
import demo.ai.aiArt.pojo.result.GetAiArtAllSamplerResult;
import demo.ai.aiArt.pojo.result.GetAiArtAllUpscalerResult;
import demo.ai.aiArt.pojo.vo.AiArtSamplerVO;
import demo.ai.aiArt.pojo.vo.AiArtUpscalerVO;
import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.aiArt.service.AiArtService;
import demo.ai.aiChat.pojo.po.AiChatUserDetail;
import demo.image.pojo.type.ImageTagType;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.result.ImageSavingResult;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;
import wechatSdk.pojo.dto.AiArtGenerateOtherLikeThatDTO;
import wechatSdk.pojo.type.WechatSdkCommonResultType;

@Service
public class AiArtServiceImpl extends AiArtCommonService implements AiArtService {

	@Autowired
	private AiArtTextToImageProducer aiArtTextToImageProducer;
	@Autowired
	private FileUtilCustom fileUtilCustom;
	@Autowired
	private AiArtModelMapper aiArtModelMapper;

	@Override
	public SendTextToImgJobResult sendTextToImgFromWechatDtoToMq(TextToImageFromWechatDTO dto) {
		SendTextToImgJobResult r = new SendTextToImgJobResult();
		if (StringUtils.isBlank(dto.getTmpKey())) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		Long aiUserId = getAiChatUserIdByTempKey(dto.getTmpKey());
		if (aiUserId == null) {
			r.setMessage(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getName());
			r.setCode(String.valueOf(WechatSdkCommonResultType.TMP_KEY_EXPIRED.getCode()));
			return r;
		}

		extendTmpKeyValidity(Long.parseLong(dto.getTmpKey()));
		SendTextToImgJobResult addJobResult = sendTextToImgDtoToMq(aiUserId, dto, false);

		if (dto.getNoticeWhenComplete() != null && dto.getNoticeWhenComplete() && addJobResult.isSuccess()) {
			addNoticeWhenCompleteMark(aiUserId, systemOptionService.decryptPrivateKey(addJobResult.getJobPk()));
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

		AiChatUserDetail aiUser = aiChatUserService.__getUserDetail(aiUserId);
		if (aiUser == null || aiUser.getIsBlock() || aiUser.getIsDelete()) {
			r.setMessage("暂时无法为您新增绘图任务, 请返回微信给管理员留言; 请勿频繁生成违规内容, 谢谢!");
			return r;
		}

		Integer freeJobCounting = getFreeJobCountingOfToday(aiUserId);
		boolean isFreeJobFlag = (freeJobCounting <= aiArtOptionService.getMaxDailyFreeJobCount());

		if (dto.getEnableHr() != null && dto.getEnableHr()) {
			if (dto.getHrUpscalerCode() == null) {
				r.setMessage("Please select a highres upscaler");
				return r;
			}
			AiArtUpscalerType upscalerType = AiArtUpscalerType.getType(dto.getHrUpscalerCode());
			if (upscalerType == null) {
				r.setMessage("Please select a highres upscaler");
				return r;
			}
			dto.setHrUpscalerName(upscalerType.getName());
			if (dto.getDenoisingStrength() < 0 || dto.getDenoisingStrength() > 1) {
				r.setMessage("Denoising strength should between 0 to 1");
				return r;
			}
			if (dto.getHrScale() == null && (dto.getHrResizeX() == null || dto.getHrResizeY() == null)) {
				r.setMessage(
						"Please set a highres scaler condition, (Highres scale) or (Highres resize X & Highres resize Y) \n"
								+ "请设置高清修复比例 或者设置 高清修复尺寸(hrResizeX(宽) 和 hrResizeY(高))");
				return r;
			}
			if (dto.getHrResizeX() != null && dto.getHrResizeY() != null) {
				if (dto.getHrResizeX() > aiArtOptionService.getHigerFixMaxWidth()
						|| dto.getHrResizeY() > aiArtOptionService.getHigerFixMaxHeight()) {
					r.setMessage("Highres scale size length should less than "
							+ aiArtOptionService.getHigerFixMaxWidth() + "\n" + "输入的高清修复尺寸过大");
					return r;
				}
				if (dto.getHrResizeX() < 1 || dto.getHrResizeY() < 1) {
					r.setMessage("Highres resize length should bigger than 1 \n 输入的高清修复尺寸过大");
					return r;
				}
			} else {
				if (dto.getHrScale() < 1) {
					r.setMessage("Highres scale should bigger than 1 \n 输入的高清修复尺寸过大");
					return r;
				}
				double highresHeight = dto.getHrScale() * dto.getHeight();
				double highresWidth = dto.getHrScale() * dto.getWidth();
				if (highresHeight > aiArtOptionService.getHigerFixMaxHeight()
						|| highresWidth > aiArtOptionService.getHigerFixMaxWidth()) {
					r.setMessage("Highres scale size length should less than "
							+ aiArtOptionService.getHigerFixMaxWidth() + "\n" + "输入的高清修复尺寸过大");
					return r;
				}

			}

			if (dto.getHrSecondPassSteps() < 1
					|| dto.getHrSecondPassSteps() > aiArtOptionService.getHigerFixMaxStep()) {
				r.setMessage("Highres steps should between 1 to " + aiArtOptionService.getHigerFixMaxStep()
						+ "\n 高清修复步数应为 1~" + aiArtOptionService.getHigerFixMaxStep() + "步");
				return r;
			} else {
				dto.setHrSecondPassSteps(1);
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

		if (dto.getWidth() != null) {
			if (dto.getWidth() < 1 || dto.getWidth() > aiArtOptionService.getMaxWidth()) {
				r.setMessage("请设置初始宽度介于 1 ~ " + aiArtOptionService.getMaxWidth());
				return r;
			}
		} else {
			dto.setWidth(512);
		}

		if (dto.getHeight() != null) {
			if (dto.getHeight() < 1 || dto.getHeight() > aiArtOptionService.getMaxHeight()) {
				r.setMessage("请设置初始高度介于 1 ~ " + aiArtOptionService.getMaxHeight());
				return r;
			}
		} else {
			dto.setHeight(512);
		}

		if (dto.getCfgScale() != null) {
			if (dto.getCfgScale() < 1 || dto.getCfgScale() > aiArtOptionService.getMaxCfgScale()) {
				r.setMessage("Cfg scale 请设置于 1 ~ " + aiArtOptionService.getMaxCfgScale());
				return r;
			}
		} else {
			dto.setCfgScale(7);
		}

		if (dto.getSteps() != null) {
			if (dto.getSteps() < 1 || dto.getSteps() > aiArtOptionService.getMaxSteps()) {
				r.setMessage("采样步数应为 1~" + aiArtOptionService.getMaxSteps());
				return r;
			}
		} else {
			dto.setSteps(20);
		}

		if (dto.getBatchSize() != null) {
			if (dto.getBatchSize() < 1 || dto.getBatchSize() > aiArtOptionService.getMaxBatch()) {
				dto.setBatchSize(aiArtOptionService.getMaxBatch());
				r.setMessage("batch size should be 1~" + aiArtOptionService.getMaxBatch());
				return r;
			}
		} else {
			dto.setBatchSize(1);
		}

		if (dto.getSampler() == null) {
			dto.setSampler(AiArtSamplerType.Euler_A.getCode());
		} else {
			AiArtSamplerType samplerType = AiArtSamplerType.getType(dto.getSampler());
			if (samplerType == null) {
				r.setMessage("Please input correct samplerCode");
				return r;
			}
		}

		if (dto.getModelCode() == null) {
			dto.setModelCode(AiArtDefaultModelType.chilloutmix_NiPrunedFp32Fix.getCode());
			dto.setModelName(AiArtDefaultModelType.chilloutmix_NiPrunedFp32Fix.getName());
		} else {
			AiArtModel model = aiArtModelMapper.selectByPrimaryKey(dto.getModelCode().longValue());
			if (model == null) {
				r.setMessage("Model 不存在, 请输入正确参数");
				return r;
			}
			if (!model.getPublicModel() && !aiArtOptionService.getIdOfAdmin().equals(aiUserId)) {
				r.setMessage("Model 不存在, 请输入正确参数");
				return r;
			}

			dto.setModelName(model.getFileName());
		}

		// 计费之前需要保证计费用的数据正确,
		BigDecimal cost = calculateTokenCost(dto);
		if (!isFreeJobFlag) {
			AiChatUserDetail userDetail = aiChatUserService.__getUserDetail(aiUserId);
			int totalAmount = userDetail.getBonusAmount().intValue() + userDetail.getRechargeAmount().intValue();

			if (cost.intValue() > totalAmount) {
				r.setMessage("余额不足, 请到个人中心购买充值包 签到, 或留意其他活动");
				return r;
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
		AiArtTextToImageJobRecord newJobPO = new AiArtTextToImageJobRecord();
		newJobPO.setId(jobId);
		newJobPO.setIsFreeJob(isFreeJobFlag);
		newJobPO.setAiUserId(aiUserId);
		newJobPO.setJobStatus(AiArtJobStatusType.WAITING.getCode().byteValue());
		newJobPO.setIsFromApi(isFromApi);
		aiArtTextToImageJobRecordMapper.insertSelective(newJobPO);

		CommonResult saveParameterResult = saveAiArtGenerateImgResultJson(dto, new ArrayList<>());
		if (saveParameterResult.isFail()) {
			r.setMessage(saveParameterResult.getMessage());
			return r;
		}

		r.setJobPk(systemOptionService.encryptId(jobId));
		r.setIsFreeJob(isFreeJobFlag);
		aiChatUserService.__debitAmountAndAddTokenUsage(aiUserId, cost);
		if (isFreeJobFlag) {
			addFreeJobCountingOfToday(aiUserId);
			addFreeJobCountingOfLastThreeDays(aiUserId);
			aiChatUserService.recharge(aiUserId, AiServiceAmountType.BONUS, cost);
			r.setFreeJobCountLeft(aiArtOptionService.getMaxDailyFreeJobCount() - freeJobCounting);
		} else {
			r.setFreeJobCountLeft(0);
		}

		r.setIsRunning(aiArtCacheService.getIsRunning());
		r.setIsSuccess();
		return r;
	}

	private TextToImageDTO getParameterByJobId(Long jobId) {
		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);
		if (jobResult == null) {
			return null;
		}
		return jobResult.getParameter();
	}

	@Override
	public void receiveImgJobResultForMQ(String txtToImgResultStr) {
		AiArtTxtToImgResult txtToImgResult = buildObjFromJsonCustomization(txtToImgResultStr,
				AiArtTxtToImgResult.class);
		receiveImgJobResult(txtToImgResult);
	}

	@Override
	public void receiveImgJobResultForApi(JSONObject json) {
		log.error("Receive AI art json result");
		AiArtTxtToImgResult r = buildObjFromJsonCustomization(json.toString(), AiArtTxtToImgResult.class);
		log.error("Receive AI art result: " + r.getJobId() + ", code: " + r.getCode() + ", message: " + r.getMessage());
		receiveImgJobResult(r);
	}

	public void receiveImgJobResult(AiArtTxtToImgResult txtToImgResult) {
		if (txtToImgResult == null || txtToImgResult.getJobId() == null) {
			log.error("Text to image feedback error");
			return;
		}

		if (txtToImgResult.getImgBase64List() == null || txtToImgResult.getImgBase64List().isEmpty()) {
			return;
		}

		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(txtToImgResult.getJobId());
		if (jobPO == null || AiArtJobStatusType.SUCCESS.getCode().byteValue() == jobPO.getJobStatus()) {
			return;
		}
		Long jobId = txtToImgResult.getJobId();

		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);
		if (jobResult == null) {
			return;
		}

		TextToImageDTO parameterDTO = jobResult.getParameter();
		if (parameterDTO == null) {
			return;
		}

		saveResultTag: if (txtToImgResult.isSuccess()) {
			if (jobResult.getImgPkList().size() >= parameterDTO.getBatchSize()) {
				break saveResultTag;
			}

			List<String> imageListInBase64 = txtToImgResult.getImgBase64List();
			List<String> imagePkList = new ArrayList<>();
			for (int i = 0; i < imageListInBase64.size(); i++) {
				ImageSavingResult imgSavingResult = saveImg(imageListInBase64.get(i));
				if (imgSavingResult.isFail()) {
					jobPO.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
					jobPO.setRunCount(jobPO.getRunCount() + 1);
					aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);
					log.error("Can NOT save image job ID: " + jobId);
					break saveResultTag;
				}
				imagePkList.add(imgSavingResult.getImgPK());
			}
			jobResult.getImgPkList().addAll(imagePkList);
			CommonResult saveGenerateImgResultJsonResult = saveAiArtGenerateImgResultJson(parameterDTO,
					jobResult.getImgPkList());
			if (saveGenerateImgResultJsonResult.isFail()) {
				log.error("Job result saving error, jobID : " + jobId + ", error: "
						+ saveGenerateImgResultJsonResult.getMessage());
				break saveResultTag;
			}

			if (jobResult.getImgPkList().size() < parameterDTO.getBatchSize()) {
				break saveResultTag;
			}

			AiArtGeneratingRecord imgGeneratingRecord = new AiArtGeneratingRecord();
			imgGeneratingRecord.setAiUserId(jobPO.getAiUserId());
			imgGeneratingRecord.setId(parameterDTO.getJobId());
			imgGeneratingRecord.setTokens(calculateTokenCost(parameterDTO));
			aiArtGeneratingRecordMapper.insertSelective(imgGeneratingRecord);

			jobPO.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
			jobPO.setRunCount(jobPO.getRunCount() + 1);
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

		} else {
			jobPO.setJobStatus(AiArtJobStatusType.FAILED.getCode().byteValue());
			jobPO.setRunCount(jobPO.getRunCount() + 1);
			aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

			if (jobPO.getRunCount() + 1 == aiArtOptionService.getMaxFailCountForJob()) {
				if (!jobPO.getIsFreeJob()) {
					BigDecimal cost = calculateTokenCost(parameterDTO);
					aiChatUserService.recharge(jobPO.getAiUserId(), AiServiceAmountType.BONUS, cost);
					jobPO.setRunCount(jobPO.getRunCount() + 1);
					jobPO.setHasReview(true);
					aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);
					minusJobCounting(jobPO.getAiUserId());
				}
			}
		}

		removeJobInQueueMark(jobId);
	}

	@SuppressWarnings("unused")
	private CommonResult sendTxtToImgRequestWhenDev(TextToImageDTO dto) {
		AiArtTextToImageJobRecord jobPO = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(dto.getJobId());
		jobPO.setJobStatus(AiArtJobStatusType.SUCCESS.getCode().byteValue());
		jobPO.setRunCount(jobPO.getRunCount() + 1);
		aiArtTextToImageJobRecordMapper.updateByPrimaryKeySelective(jobPO);

//		Need old DTO for save, bug send SFW DTO to generate images
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
	public void rerunAllWaitingJobs() {
		if (!aiArtCacheService.getIsRunning()) {
			return;
		}
		List<AiArtTextToImageJobRecord> poList = aiArtTextToImageJobRecordMapper
				.findWaitingJobs(aiArtOptionService.getMaxFailCountForJob());
		if (poList.isEmpty()) {
			return;
		}

		for (AiArtTextToImageJobRecord po : poList) {
			if (isJobInQueue(po.getId())) {
				continue;
			}
			if (!po.getIsFreeJob()) {
				TextToImageDTO dto = getParameterByJobId(po.getId());
				aiArtTextToImageProducer.send(dto);
				return;
			}
			Long aiUserId = po.getAiUserId();
			Boolean rechargeFlag = checkRechargeMarkThisWeek(aiUserId);
			if (rechargeFlag) {
				TextToImageDTO dto = getParameterByJobId(po.getId());
				aiArtTextToImageProducer.send(dto);
				return;
			}

			Integer freeJobCountingInLastThreeDays = getFreeJobCountingOfLastThreeDays(aiUserId);
			int delaySeconds = freeJobCountingInLastThreeDays * aiArtOptionService.getFreeJobDelaySeconds();
			LocalDateTime startTime = po.getCreateTime().plusSeconds(delaySeconds);
			if (startTime.isBefore(LocalDateTime.now())) {
				TextToImageDTO dto = getParameterByJobId(po.getId());
				aiArtTextToImageProducer.send(dto);
			}
		}
	}
	
	@Override
	public TextToImageDTO findRerunJobWhenSdkAsk() {
		List<AiArtTextToImageJobRecord> poList = aiArtTextToImageJobRecordMapper
				.findWaitingJobs(aiArtOptionService.getMaxFailCountForJob());
		if (poList.isEmpty()) {
			return null;
		}

		for (AiArtTextToImageJobRecord po : poList) {
			if (isJobInQueue(po.getId())) {
				continue;
			}
			if (!po.getIsFreeJob()) {
				TextToImageDTO dto = getParameterByJobId(po.getId());
				return dto;
			}
			Long aiUserId = po.getAiUserId();
			Boolean rechargeFlag = checkRechargeMarkThisWeek(aiUserId);
			if (rechargeFlag) {
				TextToImageDTO dto = getParameterByJobId(po.getId());
				return dto;
			}

			Integer freeJobCountingInLastThreeDays = getFreeJobCountingOfLastThreeDays(aiUserId);
			int delaySeconds = freeJobCountingInLastThreeDays * aiArtOptionService.getFreeJobDelaySeconds();
			LocalDateTime startTime = po.getCreateTime().plusSeconds(delaySeconds);
			if (startTime.isBefore(LocalDateTime.now())) {
				TextToImageDTO dto = getParameterByJobId(po.getId());
				return dto;
			}
		}
		
		return null;
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
		AiArtGenerateImageQueryResult jobResult = null;
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
	public GetJobResultList getJobResultVoByJobPk(BasePkDTO dto) {
		return super.getJobResultVoByJobPk(dto.getPk());
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
	public void loadingCache() {
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
			LocalTime startTime = LocalTime.parse(aiArtOptionService.getServiceStartTimeStr());
			LocalTime endTime = LocalTime.parse(aiArtOptionService.getServiceEndTimeStr());
			aiArtCacheService.setServiceStartTime(startTime);
			aiArtCacheService.setServiceEndTime(endTime);
		} catch (Exception e) {
			sendTelegramMessage("AI art loading cache failed: " + e.getLocalizedMessage());
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
			loadingCache();
		}
		AiArtImageWallResult dto = aiArtCacheService.getImageWall();
		if (dto != null) {
			return dto;
		}
		loadingCache();
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

		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);
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

	// 2023-04-29 转为自有服务器后 可能删除
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

		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);
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

	@Override
	public List<AiArtModelVO> getAiArtModelVoList() {
		AiArtModelExample example = new AiArtModelExample();
		example.createCriteria().andIsDeleteEqualTo(false).andPublicModelEqualTo(true);
		List<AiArtModel> modelList = aiArtModelMapper.selectByExample(example);
		AiArtModelVO vo = null;
		List<AiArtModelVO> voList = new ArrayList<>();
		if (modelList.isEmpty()) {
			return voList;
		}
		for (AiArtModel po : modelList) {
			vo = new AiArtModelVO();
			vo.setModelId(po.getId().intValue());
			vo.setModelName(po.getDisplayName());
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public GetAiArtAllModelListResult getAllModelList() {
		GetAiArtAllModelListResult r = new GetAiArtAllModelListResult();
		r.setIsSuccess();
		List<AiArtModelVO> modelList = getAiArtModelVoList();
		r.setModelList(modelList);
		return r;
	}

	@Override
	public GetAiArtAllSamplerResult getAllSamplerList() {
		GetAiArtAllSamplerResult r = new GetAiArtAllSamplerResult();
		r.setIsSuccess();
		r.setSamplerList(new ArrayList<>());
		AiArtSamplerType[] values = AiArtSamplerType.values();
		AiArtSamplerVO vo = null;
		for (int i = 0; i < values.length; i++) {
			vo = new AiArtSamplerVO();
			vo.setCode(values[i].getCode());
			vo.setName(values[i].getName());
			r.getSamplerList().add(vo);
		}
		return r;
	}

	@Override
	public GetAiArtAllUpscalerResult getAllUpsalerList() {
		GetAiArtAllUpscalerResult r = new GetAiArtAllUpscalerResult();
		r.setIsSuccess();
		r.setUpscalerList(new ArrayList<>());
		AiArtUpscalerType[] values = AiArtUpscalerType.values();
		AiArtUpscalerVO vo = null;
		for (int i = 0; i < values.length; i++) {
			vo = new AiArtUpscalerVO();
			vo.setCode(values[i].getCode());
			vo.setName(values[i].getName());
			r.getUpscalerList().add(vo);
		}
		return r;
	}

	@Override
	public void heartBeatReciver() {
		aiArtCacheService.setIsRunning(true);
		aiArtCacheService.setLastHearBeatTime(LocalDateTime.now());
	}
}
