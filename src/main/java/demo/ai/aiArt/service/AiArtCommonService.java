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
import ai.aiArt.pojo.result.GetJobResultList;
import ai.aiArt.pojo.type.AiArtJobStatusType;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiArt.mapper.AiArtGeneratingRecordMapper;
import demo.ai.aiArt.mapper.AiArtTextToImageJobRecordMapper;
import demo.ai.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.ai.aiArt.service.impl.AiArtCacheService;
import demo.ai.aiArt.service.impl.AiArtOptionService;
import demo.ai.aiChat.service.AiChatUserService;
import demo.ai.common.service.impl.AiCommonService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.image.service.ImageService;
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
	protected ImageService imageService;

	@Autowired
	protected AiChatUserService aiChatUserService;

	@Autowired
	private FileUtilCustom fileUtilCustom;
	@Autowired
	private RedisOriginalConnectService redisConnectService;

	private final String AI_ART_FREE_JOB_COUNTING_OF_TODAY_REDIS_KEY_PREFIX = "aiArtFreeJobCountingOfToday_";
	private final String AI_ART_FREE_JOB_COUNTING_OF_LAST_THREE_DAYS_REDIS_KEY_PREFIX = "aiArtFreeJobCountingOfLastThreeDays_";
	private final String AI_ART_JOB_IN_QUEUE_REDIS_KEY_PREFIX = "aiArtJobInQueue_";
	private final String AI_ART_NSFW_JOB_COUNTING_REDIS_KEY_PREFIX = "aiArtNsfwJobCounting_";
	private final String AI_ART_NOTICE_WHEN_COMPLETE_REDIS_KEY_PREFIX = "aiArtNoticeWhenComplete_";

	protected void addFreeJobCountingOfToday(Long aiUserId) {
		Integer count = getFreeJobCountingOfToday(aiUserId);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayMax = now.with(LocalTime.MAX);
		long minutes = ChronoUnit.MINUTES.between(now, todayMax);
		redisConnectService.setValByName(AI_ART_FREE_JOB_COUNTING_OF_TODAY_REDIS_KEY_PREFIX + String.valueOf(aiUserId),
				String.valueOf(count + 1), minutes, TimeUnit.MINUTES);
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
		String countStr = redisConnectService
				.getValByName(AI_ART_FREE_JOB_COUNTING_OF_TODAY_REDIS_KEY_PREFIX + String.valueOf(aiUserId));
		Integer count = 0;
		try {
			count = Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		return count;
	}

	protected void addFreeJobCountingOfLastThreeDays(Long aiUserId) {
		Integer count = getFreeJobCountingOfLastThreeDays(aiUserId);
		redisConnectService.setValByName(
				AI_ART_FREE_JOB_COUNTING_OF_LAST_THREE_DAYS_REDIS_KEY_PREFIX + String.valueOf(aiUserId),
				String.valueOf(count + 1), 3, TimeUnit.DAYS);
	}

	protected Integer getFreeJobCountingOfLastThreeDays(Long aiUserId) {
		String countStr = redisConnectService
				.getValByName(AI_ART_FREE_JOB_COUNTING_OF_LAST_THREE_DAYS_REDIS_KEY_PREFIX + String.valueOf(aiUserId));
		Integer count = 0;
		try {
			count = Integer.parseInt(countStr);
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

	protected AiArtGenerateImageVO buildAiArtGenerateImageVO(AiArtTextToImageJobRecord po,
			AiArtGenerateImageQueryResult subResult, String jobPk) {
		return __buildAiArtGenerateImageVO(po, subResult, jobPk);
	}

	private AiArtGenerateImageVO __buildAiArtGenerateImageVO(AiArtTextToImageJobRecord po,
			AiArtGenerateImageQueryResult subResult, String jobPk) {
		AiArtGenerateImageVO vo = new AiArtGenerateImageVO();
		boolean forAdmin = isBigUser();
		vo = new AiArtGenerateImageVO();
		vo.setJobPk(jobPk);
		vo.setAiUserPk(systemOptionService.encryptId(po.getAiUserId()));
		vo.setRunCount(po.getRunCount());
		vo.setCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		vo.setIsDelete(po.getIsDelete());
		vo.setIsFromApi(po.getIsFromApi());
		if (forAdmin) {
			vo.setIsFreeJob(po.getIsFreeJob());
			vo.setHasReview(po.getHasReview());
			vo.setJobStatus(po.getJobStatus().intValue());
			vo.setNsfwJobCounting(getNsfwJobCounting(po.getAiUserId()));
		} else {
			if (!po.getHasReview()) {
				vo.setJobStatus(AiArtJobStatusType.WAITING.getCode());
			} else {
				vo.setJobStatus(po.getJobStatus().intValue());
			}
		}

		if (subResult != null && (forAdmin || po.getHasReview())) {
			List<String> imgUrlList = new ArrayList<>();
			if (subResult.getImgPkList() != null && !subResult.getImgPkList().isEmpty()) {
				for (String imgUrl : subResult.getImgPkList()) {
					imgUrlList.add(imgUrl);
				}
			}
			vo.setImgPkList(imgUrlList);
			TextToImageDTO subParam = subResult.getParameter();
			subParam.setJobId(null);
//			if (subParam.getPrompts() != null) {
//				subParam.setPrompts(
//						subParam.getPrompts().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
//				subParam.setNegativePrompts(subParam.getNegativePrompts().replaceAll("&", "&amp;")
//						.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
//			}
			vo.setParameter(subParam);
		}
		return vo;
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
		return result;
	}

	protected String getJobResultStrPath(Long jobId) {
		String resultJsonSavePath = aiArtOptionService.getGenerateImageResultFolder() + "/" + jobId + ".json";
		return resultJsonSavePath;
	}

	protected BigDecimal calculateTokenCost(TextToImageDTO dto) {
		return new BigDecimal(dto.getWidth().longValue() * dto.getHeight().longValue() * dto.getSteps().longValue()
				* dto.getBatchSize() * aiArtOptionService.getConsumptionCoefficient())
				.setScale(0, RoundingMode.CEILING);
	}

	protected CommonResult saveAiArtGenerateImgResultJson(TextToImageDTO dto, List<String> imgPkList) {
		CommonResult r = new CommonResult();
		AiArtGenerateImageQueryResult result = new AiArtGenerateImageQueryResult();
		result.setParameter(dto);
		result.setImgPkList(imgPkList);

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

	protected GetJobResultList getJobResultVoByJobPk(String jobPk) {
		GetJobResultList r = new GetJobResultList();
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
			List<String> newImgPkList = new ArrayList<>();
			for (String imgPk : jobResult.getImgPkList()) {
				updateImageInvalidTimeByImgUrl(imgPk);
				newImgPkList.add(imgPk);
			}
			jobResult.setImgPkList(newImgPkList);
		}

		AiArtGenerateImageVO vo = buildAiArtGenerateImageVO(po, jobResult, jobPk);
		r.setJobResultList(new ArrayList<>());
		if (!po.getIsFromApi() && !po.getHasReview()) {
			vo.setJobStatus(AiArtJobStatusType.WAITING.getCode());
			vo.setImgPkList(new ArrayList<>());
		}

		r.getJobResultList().add(vo);
		r.setIsSuccess();
		return r;
	}

	protected void updateImageInvalidTimeByImgUrl(String imgPk) {
		if (aiArtOptionService.getImagePkInsteadOfNsfw().equals(imgPk)) {
			return;
		}
		Long imgId = systemOptionService.decryptPrivateKey(imgPk);
		imageService.shortenImageValidTime(imgId,
				LocalDateTime.now().plusMinutes(aiArtOptionService.getMaxLivingMinuteOfApiImageAfterFirstVisit()));
	}
}
