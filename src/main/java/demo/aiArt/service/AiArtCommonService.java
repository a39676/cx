package demo.aiArt.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import ai.aiArt.pojo.dto.TextToImageFromDTO;
import ai.aiArt.pojo.result.AiArtGenerateImageResult;
import ai.aiArt.pojo.vo.AiArtGenerateImageVO;
import demo.aiArt.pojo.po.AiArtTextToImageJobRecord;
import demo.aiArt.service.impl.AiArtOptionService;
import demo.aiChat.service.impl.AiChatCommonService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import toolPack.ioHandle.FileUtilCustom;

public abstract class AiArtCommonService extends AiChatCommonService {

	@Autowired
	protected AiArtOptionService aiArtOptionService;

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
			if (!subResult.getImgUrlList().isEmpty()) {
				for (String imgUrl : subResult.getImgUrlList()) {
					imgUrlList.add(imgUrl);
				}
			}
			vo.setImgUrlList(imgUrlList);
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
}
