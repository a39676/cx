package demo.ai.aiArt.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ai.aiArt.pojo.dto.TextToImageDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageQueryResult;
import ai.aiArt.pojo.result.GetJobResultListForUser;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.type.AiArtSamplerType;
import ai.aiArt.pojo.vo.AiArtGenerateImageAdminVO;
import ai.aiArt.pojo.vo.AiArtGenerateImageUserVO;
import ai.aiArt.pojo.vo.ImgVO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.mapper.AiArtGeneratingRecordMapper;
import demo.ai.aiArt.mapper.AiArtImageWallMapper;
import demo.ai.aiArt.mapper.AiArtModelMapper;
import demo.ai.aiArt.mapper.AiArtTextToImageJobRecordMapper;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.service.impl.AiArtCacheService;
import demo.ai.aiArt.service.impl.AiArtOptionService;
import demo.ai.aiChat.mapper.AiChatUserAssociateWechatUidMapper;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidExample;
import demo.ai.aiChat.pojo.po.AiChatUserAssociateWechatUidKey;
import demo.ai.aiChat.service.AiUserService;
import demo.ai.common.service.impl.AiCommonService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.image.pojo.result.GetImgThirdPartyUrlInBatchResult;
import demo.image.service.ImageService;
import demo.interaction.wechat.mq.producer.SendAiArtJobCompleteTemplateMessageProducer;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

public abstract class AiArtCommonService extends AiCommonService {

	@Autowired
	protected AiArtOptionService aiArtOptionService;
	@Autowired
	protected AiArtCacheService aiArtCacheService;
	@Autowired
	protected AiArtGeneratingRecordMapper aiArtGeneratingRecordMapper;
	@Autowired
	protected AiArtTextToImageJobRecordMapper aiArtTextToImageJobRecordMapper;
	@Autowired
	protected AiArtModelMapper aiArtModelMapper;
	@Autowired
	protected AiArtImageWallMapper aiArtImageWallMapper;
	@Autowired
	protected ImageService imageService;

	@Autowired
	protected AiUserService aiChatUserService;

	@Autowired
	private SendAiArtJobCompleteTemplateMessageProducer sendAiArtJobCompleteTemplateMessageProducer;
	@Autowired
	private AiChatUserAssociateWechatUidMapper aiChatUserAssociateWechatUidMapper;

	@Autowired
	private FileUtilCustom fileUtilCustom;
	@Autowired
	private RedisOriginalConnectService redisConnectService;

	private final String AI_ART_FREE_JOB_COUNTING_BY_DATE_REDIS_KEY_PREFIX = "aiArtFreeJobCountingByDate_";
	private final String AI_ART_JOB_IN_QUEUE_REDIS_KEY_PREFIX = "aiArtJobInQueue_";
	private final String AI_ART_NSFW_JOB_COUNTING_REDIS_KEY_PREFIX = "aiArtNsfwJobCounting_";
	private final String AI_ART_NOTICE_WHEN_COMPLETE_REDIS_KEY_PREFIX = "aiArtNoticeWhenComplete_";

	protected void addFreeJobCountingOfToday(Long aiUserId) {
		Integer count = getFreeJobCountingOfToday(aiUserId);
		LocalDateTime now = LocalDateTime.now();
		String key = AI_ART_FREE_JOB_COUNTING_BY_DATE_REDIS_KEY_PREFIX + now.getMonthValue() + now.getDayOfMonth() + "_"
				+ String.valueOf(aiUserId);
		redisConnectService.setValByName(key, String.valueOf(count + 1), 3, TimeUnit.DAYS);
	}

	protected void addNsfwJobCounting(Long aiUserId) {
		Integer count = getNsfwJobCounting(aiUserId);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayMax = now.with(LocalTime.MAX);
		long minutes = ChronoUnit.MINUTES.between(now, todayMax);
		redisConnectService.setValByName(AI_ART_NSFW_JOB_COUNTING_REDIS_KEY_PREFIX + String.valueOf(aiUserId),
				String.valueOf(count + 1), minutes, TimeUnit.MINUTES);
	}

	protected void minusJobCounting(Long aiUserId) {
		Integer count = getFreeJobCountingOfToday(aiUserId);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayMax = now.with(LocalTime.MAX);
		long minutes = ChronoUnit.MINUTES.between(now, todayMax);
		redisConnectService.setValByName(String.valueOf(aiUserId), String.valueOf(count - 1), minutes,
				TimeUnit.MINUTES);
	}

	protected Integer getFreeJobCountingOfToday(Long aiUserId) {
		LocalDateTime now = LocalDateTime.now();
		String key = AI_ART_FREE_JOB_COUNTING_BY_DATE_REDIS_KEY_PREFIX + now.getMonthValue() + now.getDayOfMonth() + "_"
				+ String.valueOf(aiUserId);
		String countStr = redisConnectService.getValByName(key);
		Integer count = 0;
		try {
			count = Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		return count;
	}

	protected Integer getFreeJobCountingOfLastThreeDays(Long aiUserId) {
		LocalDateTime now = LocalDateTime.now();
		String todayKey = AI_ART_FREE_JOB_COUNTING_BY_DATE_REDIS_KEY_PREFIX + now.getMonthValue() + now.getDayOfMonth()
				+ "_" + String.valueOf(aiUserId);
		LocalDateTime yesterday = now.minusDays(1);
		String yesterdayKey = AI_ART_FREE_JOB_COUNTING_BY_DATE_REDIS_KEY_PREFIX + yesterday.getMonthValue()
				+ yesterday.getDayOfMonth() + "_" + String.valueOf(aiUserId);
		LocalDateTime towDaysAgo = now.minusDays(2);
		String towDaysAgoKey = AI_ART_FREE_JOB_COUNTING_BY_DATE_REDIS_KEY_PREFIX + towDaysAgo.getMonthValue()
				+ towDaysAgo.getDayOfMonth() + "_" + String.valueOf(aiUserId);

		String countStr = null;
		Integer count = 0;
		try {
			countStr = redisConnectService.getValByName(todayKey);
			count += Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		try {
			countStr = redisConnectService.getValByName(yesterdayKey);
			count += Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		try {
			countStr = redisConnectService.getValByName(towDaysAgoKey);
			count += Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		return count;
	}

	protected Integer getNsfwJobCounting(Long aiUserId) {
		String countStr = redisConnectService
				.getValByName(AI_ART_NSFW_JOB_COUNTING_REDIS_KEY_PREFIX + String.valueOf(aiUserId));
		Integer count = 0;
		try {
			count = Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		return count;
	}

	protected void addNoticeWhenCompleteMark(Long aiUserId, Long jobId) {
		redisConnectService.setValByName(
				AI_ART_NOTICE_WHEN_COMPLETE_REDIS_KEY_PREFIX + String.valueOf(aiUserId) + "_" + String.valueOf(jobId),
				"", 3, TimeUnit.DAYS);
	}

	protected void removeNoticeWhenCompleteMark(Long aiUserId, Long jobId) {
		redisConnectService.deleteValByName(
				AI_ART_NOTICE_WHEN_COMPLETE_REDIS_KEY_PREFIX + String.valueOf(aiUserId) + "_" + String.valueOf(jobId));
	}

	protected void addJobInQueueMark(Long jobId) {
		redisConnectService.setValByName(AI_ART_JOB_IN_QUEUE_REDIS_KEY_PREFIX + "_" + String.valueOf(jobId), "", 1,
				TimeUnit.DAYS);
	}

	protected void removeJobInQueueMark(Long jobId) {
		redisConnectService.deleteValByName(AI_ART_JOB_IN_QUEUE_REDIS_KEY_PREFIX + "_" + String.valueOf(jobId));
	}

	protected boolean isJobInQueue(Long jobId) {
		return redisConnectService.hasKey(AI_ART_JOB_IN_QUEUE_REDIS_KEY_PREFIX + "_" + String.valueOf(jobId));
	}

	protected boolean hasNoticeWhenCompleteMark(Long aiUserId, Long jobId) {
		return redisConnectService.hasKey(
				AI_ART_NOTICE_WHEN_COMPLETE_REDIS_KEY_PREFIX + String.valueOf(aiUserId) + "_" + String.valueOf(jobId));
	}

	protected AiArtGenerateImageUserVO buildAiArtGenerateImageVoForUser(AiArtTextToImageJobRecord po,
			AiArtGenerateImageQueryResult subResult, String jobPk) {
		AiArtGenerateImageAdminVO adminVO = buildAiArtGenerateImageVoForAdmin(po, subResult, jobPk);
		AiArtGenerateImageUserVO userVO = new AiArtGenerateImageUserVO();
		userVO.setCreateTimeStr(adminVO.getCreateTimeStr());
		userVO.setImgVoList(adminVO.getImgVoList());
		userVO.setIsFreeJob(adminVO.getIsFreeJob());
		userVO.setJobPk(adminVO.getJobPk());
		userVO.setJobStatus(adminVO.getJobStatus());
		userVO.setModelName(adminVO.getModelName());
		userVO.setParameter(adminVO.getParameter());
		userVO.setRunCount(adminVO.getRunCount());
		userVO.setSamplerName(adminVO.getSamplerName());
		return userVO;
	}

	protected AiArtGenerateImageAdminVO buildAiArtGenerateImageVoForAdmin(AiArtTextToImageJobRecord po,
			AiArtGenerateImageQueryResult subResult, String jobPk) {
		return __buildAiArtGenerateImageVO(po, subResult, jobPk);
	}

	private AiArtGenerateImageAdminVO __buildAiArtGenerateImageVO(AiArtTextToImageJobRecord po,
			AiArtGenerateImageQueryResult subResult, String jobPk) {
		AiArtGenerateImageAdminVO jobResultVO = new AiArtGenerateImageAdminVO();
		boolean forAdmin = isBigUser();
		jobResultVO = new AiArtGenerateImageAdminVO();
		jobResultVO.setJobPk(jobPk);
		jobResultVO.setAiUserPk(systemOptionService.encryptId(po.getAiUserId()));
		jobResultVO.setRunCount(po.getRunCount());
		jobResultVO.setCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		jobResultVO.setIsDelete(po.getIsDelete());
		jobResultVO.setIsFromApi(po.getIsFromApi());

		if (forAdmin) {
			jobResultVO.setIsFreeJob(po.getIsFreeJob());
			jobResultVO.setHasReview(po.getHasReview());
			jobResultVO.setJobStatus(po.getJobStatus().intValue());
			jobResultVO.setNsfwJobCounting(getNsfwJobCounting(po.getAiUserId()));
		} else {
			if (!po.getHasReview() && !po.getIsFromApi()) {
				jobResultVO.setJobStatus(AiArtJobStatusType.WAITING.getCode());
			} else {
				jobResultVO.setJobStatus(po.getJobStatus().intValue());
			}
		}

		if (subResult == null) {
			return jobResultVO;
		}

		TextToImageDTO subParam = subResult.getParameter();
		subParam.setJobId(null);
//		if (subParam.getPrompts() != null) {
//			subParam.setPrompts(
//					subParam.getPrompts().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
//			subParam.setNegativePrompts(subParam.getNegativePrompts().replaceAll("&", "&amp;")
//					.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
//		}
		jobResultVO.setParameter(subParam);

		AiArtSamplerType samplerType = AiArtSamplerType.getType(subParam.getSampler());
		if (samplerType != null) {
			jobResultVO.setSamplerName(samplerType.getName());
		}

		jobResultVO.setModelName(subParam.getModelName());

		if (forAdmin || (!po.getIsFromApi() && po.getHasReview())
				|| (po.getIsFromApi() && subResult.getImgVoList().size() == subResult.getParameter().getBatchSize())) {

			if (subResult.getImgVoList() != null && !subResult.getImgVoList().isEmpty()) {
				GetImgThirdPartyUrlInBatchResult imgUrlThirdPartyResult = imageService
						.getImgThirdPartyUrlBatchResultByPk(subResult.getImgPkList());

				if (imgUrlThirdPartyResult.isFail()) {
					jobResultVO.setImgVoList(subResult.getImgVoList());
					return jobResultVO;
				}

				List<ImgVO> imgVoList = new ArrayList<>();
				for (ImgVO imgVO : subResult.getImgVoList()) {
					if (imgUrlThirdPartyResult.getImgPkMatchUrl().containsKey(imgVO.getImgPk())) {
						imgVO.setImgUrl(imgUrlThirdPartyResult.getImgPkMatchUrl().get(imgVO.getImgPk()));
					}
					imgVoList.add(imgVO);
				}
				jobResultVO.setImgVoList(imgVoList);
			}

		}
		return jobResultVO;
	}

	protected AiArtGenerateImageQueryResult getJobResult(Long jobId) {
		String resultJsonSavePath = getJobResultStrPath(jobId);
		String content = null;
		File resultFile = new File(resultJsonSavePath);
		if (!resultFile.exists()) {
			return null;
		}
		content = fileUtilCustom.getStringFromFile(resultJsonSavePath);
		AiArtGenerateImageQueryResult result = buildObjFromJsonCustomization(content,
				AiArtGenerateImageQueryResult.class);
//		TODO imageHostUpdate delete it
		if (result.getImgVoList() == null) {
			result.setImgVoList(new ArrayList<>());
		}
		if (result.getImgPkList() != null && !result.getImgPkList().isEmpty()) {
			for (String imgPk : result.getImgPkList()) {
				ImgVO vo = new ImgVO();
				vo.setImgPk(imgPk);
				result.getImgVoList().add(vo);
			}
		}

		return result;
	}

	protected String getJobResultStrPath(Long jobId) {
		String resultJsonSavePath = aiArtOptionService.getGenerateImageResultFolder() + "/" + jobId + ".json";
		return resultJsonSavePath;
	}

	protected BigDecimal calculateTokenCost(TextToImageDTO dto) {
		BigDecimal cost = new BigDecimal(dto.getWidth().longValue() * dto.getHeight().longValue()
				* dto.getSteps().longValue() * dto.getBatchSize() * aiArtOptionService.getConsumptionCoefficient())
				.setScale(0, RoundingMode.CEILING);
		if (dto.getEnableHr()) {
			Double pixelArea = 0D;
			if (dto.getHrScale() != null) {
				pixelArea = dto.getWidth() * dto.getHeight() * dto.getHrScale();
			} else {
				pixelArea = dto.getHrResizeX() * dto.getHrResizeY().doubleValue();
			}

			Double highresCost = pixelArea * dto.getHrSecondPassSteps() * dto.getDenoisingStrength()
					* aiArtOptionService.getConsumptionCoefficient();
			cost = cost.add(new BigDecimal(highresCost));
		}
		return cost;
	}

	protected CommonResult saveAiArtGenerateImgResultJson(TextToImageDTO dto, List<ImgVO> imgVoList) {
		CommonResult r = new CommonResult();
		AiArtGenerateImageQueryResult result = new AiArtGenerateImageQueryResult();
		result.setParameter(dto);
		result.setImgVoList(imgVoList);

		String resultJsonSavePath = getJobResultStrPath(dto.getJobId());
		File resultJsonFile = new File(resultJsonSavePath);

		File parentFolder = new File(aiArtOptionService.getGenerateImageResultFolder());
		if (!parentFolder.exists()) {
			if (!parentFolder.mkdirs()) {
				sendTelegramMessage("AI生成图片异常, 无法创建保存设定数据文件夹: " + parentFolder.getAbsolutePath());
				return r;
			}
		}

		JSONObject resultJson = JSONObject.fromObject(result);
		try {
			FileUtils.writeByteArrayToFile(resultJsonFile, resultJson.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
			sendTelegramMessage("AI生成图片异常, 无法保存设定数据: " + e.getLocalizedMessage());
			return r;
		}

		r.setIsSuccess();
		return r;
	}

	protected GetJobResultListForUser getJobResultVoByJobPk(String jobPk) {
		GetJobResultListForUser r = new GetJobResultListForUser();
		if (StringUtils.isBlank(jobPk)) {
			r.setMessage("Please fill the job PK");
			return r;
		}

		Long jobId = systemOptionService.decryptPrivateKey(jobPk);
		if (jobId == null) {
			r.setMessage("Please fill correct job PK");
			return r;
		}

		AiArtTextToImageJobRecord po = aiArtTextToImageJobRecordMapper.selectByPrimaryKey(jobId);
		if (po == null) {
			r.setMessage("Job expired or NOT exists");
			return r;
		}

		AiArtGenerateImageQueryResult jobResult = getJobResult(jobId);
		if (po.getIsFromApi() && jobResult != null) {
			for (ImgVO imgVO : jobResult.getImgVoList()) {
				updateImageInvalidTimeByImgPk(imgVO.getImgPk());
			}
		}

		AiArtGenerateImageUserVO vo = buildAiArtGenerateImageVoForUser(po, jobResult, jobPk);
		r.setJobResultList(new ArrayList<>());
		if (!po.getIsFromApi() && !po.getHasReview()) {
			vo.setJobStatus(AiArtJobStatusType.WAITING.getCode());
			vo.setImgVoList(new ArrayList<>());
		}

		r.getJobResultList().add(vo);
		r.setIsSuccess();
		return r;
	}

	protected void updateImageInvalidTimeByImgPk(String imgPk) {
		if (aiArtOptionService.getImagePkInsteadOfNsfw().equals(imgPk)) {
			return;
		}
		Long imgId = systemOptionService.decryptPrivateKey(imgPk);
		imageService.shortenImageValidTime(imgId,
				LocalDateTime.now().plusMinutes(aiArtOptionService.getMaxLivingMinuteOfApiImageAfterFirstVisit()));
	}

	protected void sendAiArtJobCompleteNoticeIfNecessary(Long aiUserId, Long jobId) {
		if (hasNoticeWhenCompleteMark(aiUserId, jobId)) {
			AiChatUserAssociateWechatUidExample associateExample = new AiChatUserAssociateWechatUidExample();
			associateExample.createCriteria().andAiChatUserIdEqualTo(aiUserId);
			List<AiChatUserAssociateWechatUidKey> associateList = aiChatUserAssociateWechatUidMapper
					.selectByExample(associateExample);
			if (!associateList.isEmpty()) {
				removeNoticeWhenCompleteMark(aiUserId, jobId);
				String openId = wechatSdkForInterService
						.getWechatOpenIdByWechatUserId(associateList.get(0).getWechatId());
				sendAiArtJobCompleteTemplateMessageProducer.send(openId);
			}
		}
	}
}
