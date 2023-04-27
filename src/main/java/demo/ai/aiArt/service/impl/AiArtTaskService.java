package demo.ai.aiArt.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.aiArt.service.AiArtManagerService;
import demo.ai.aiArt.service.AiArtService;

@Component
public class AiArtTaskService extends AiArtCommonService {

	@Autowired
	private AiArtService aiArtService;
	@Autowired
	private AiArtManagerService aiArtManagerService;
	@Autowired
	private AiArtCacheService aiArtCacheService;

	@Scheduled(fixedDelay = 1000L * 60 * 2)
	public void rerun() {
		if (aiArtCacheService.getIsRunning()) {
			aiArtService.rerun();
			aiArtManagerService.setReviewBatchForAdmin();
			aiArtService.sendNoticeIfAnyJobsWaitingForReview();
		}
	}

	@Scheduled(fixedDelay = 1000L * 60 * 10)
	public void insertJobToKeepLive() {
		if (aiArtCacheService.getIsRunning()) {
			aiArtService.__sendRandomGenerateJob();
		}
	}

	@Scheduled(cron = "06 05 04 * * *")
	public void deleteParameterFile() {
		aiArtService.deleteParameterFile();
	}

	@Scheduled(fixedDelay = 1000L * 60)
	public void checkAutomaticHeartBeat() {
		if (aiArtCacheService.getLastHearBeatTime().isBefore(LocalDateTime.now().minusSeconds(90))) {
			aiArtCacheService.setIsRunning(false);
			sendTelegramMessage("Automatic SDK down");
		}
	}
}
