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

import ai.aiArt.pojo.dto.TextToImageFromDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.result.GetJobResultList;
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

	protected void addJobCounting(Long aiUserId) {
		Integer count = getJobCounting(aiUserId);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime todayMax = now.with(LocalTime.MAX);
		long minutes = ChronoUnit.MINUTES.between(now, todayMax);
		redisConnectService.setValByName(String.valueOf(aiUserId), String.valueOf(count + 1), minutes,
				TimeUnit.MINUTES);
	}

	protected Integer getJobCounting(Long aiUserId) {
		String countStr = redisConnectService.getValByName(String.valueOf(aiUserId));
		Integer count = 0;
		try {
			count = Integer.parseInt(countStr);
		} catch (Exception e) {
		}
		return count;
	}

	protected AiArtGenerateImageVO buildAiArtGenerateImageVO(AiArtTextToImageJobRecord po,
			AiArtGenerateImageResult subResult, String jobPk) {
		AiArtGenerateImageVO vo = new AiArtGenerateImageVO();
		vo = new AiArtGenerateImageVO();
		vo.setJobPk(jobPk);
		vo.setAiUserPk(systemOptionService.encryptId(po.getAiUserId()));
		vo.setJobStatus(po.getJobStatus().intValue());
		vo.setRunCount(po.getRunCount());
		vo.setCreateTimeStr(localDateTimeHandler.dateToStr(po.getCreateTime()));
		vo.setIsDelete(po.getIsDelete());
		vo.setIsFromApi(po.getIsFromApi());

		if (subResult != null) {
			List<String> imgUrlList = new ArrayList<>();
			if (subResult.getImgPkList() != null && !subResult.getImgPkList().isEmpty()) {
				for (String imgUrl : subResult.getImgPkList()) {
					imgUrlList.add(imgUrl);
				}
			}
			vo.setImgPkList(imgUrlList);
			TextToImageFromDTO subParam = subResult.getParameter();
			subParam.setJobId(null);
			vo.setParameter(subParam);
		}
		return vo;
	}

	protected AiArtGenerateImageResult getJobResult(Long jobId) {
		String resultJsonSavePath = getJobResultStrPath(jobId);
		String content = null;
		try {
			content = fileUtilCustom.getStringFromFile(resultJsonSavePath);
		} catch (Exception e) {
			return null;
		}
		AiArtGenerateImageResult result = buildObjFromJsonCustomization(content, AiArtGenerateImageResult.class);
		return result;
	}

	protected String getJobResultStrPath(Long jobId) {
		String resultJsonSavePath = aiArtOptionService.getGenerateImageResultFolder() + "/" + jobId + ".json";
		return resultJsonSavePath;
	}

	protected BigDecimal calculateTokenCost(TextToImageFromDTO dto) {
		return new BigDecimal(dto.getWidth().longValue() * dto.getHeight().longValue() * dto.getSteps().longValue()
				* dto.getBatchSize() * aiArtOptionService.getConsumptionCoefficient())
				.setScale(0, RoundingMode.CEILING);
	}

	protected CommonResult saveAiArtGenerateImgResultJson(TextToImageFromDTO dto, List<String> imgPkList) {
		CommonResult r = new CommonResult();
		AiArtGenerateImageResult result = new AiArtGenerateImageResult();
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

		AiArtGenerateImageResult jobResult = getJobResult(jobId);
		if (jobResult != null) {
			List<String> newImgPkList = new ArrayList<>();
			for (String imgPk : jobResult.getImgPkList()) {
				updateImageInvalidTimeByImgUrl(imgPk);
				newImgPkList.add(imgPk);
			}
			jobResult.setImgPkList(newImgPkList);
		}

		AiArtGenerateImageVO vo = buildAiArtGenerateImageVO(po, jobResult, jobPk);
		r.setJobResultList(new ArrayList<>());
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
