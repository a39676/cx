package demo.ai.aiArt.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

	@Scheduled(fixedDelay = 1000L * 30)
	public void rerun() {
		if (aiArtCacheService.getIsRunning()) {
//			aiArtService.rerunAllWaitingJobs();
			aiArtManagerService.setReviewBatchForAdmin();
			aiArtService.sendNoticeIfAnyJobsWaitingForReview();
		}
	}

	@Scheduled(fixedDelay = 1000L * 60)
	public void checkAutomaticHeartBeat() {
		if (aiArtCacheService.getServiceStartTime() == null) {
			return;
		}
		LocalTime now = LocalTime.now();
		if (now.isBefore(aiArtCacheService.getServiceStartTime())
				|| now.isAfter(aiArtCacheService.getServiceEndTime())) {
			return;
		}
		if (aiArtCacheService.getLastHearBeatTime() == null
				|| aiArtCacheService.getLastHearBeatTime().isBefore(LocalDateTime.now().minusSeconds(90))) {
			aiArtCacheService.setIsRunning(false);
			sendTelegramMessage("Automatic SDK down, last heart beat: " + aiArtCacheService.getLastHearBeatTime());
		}
	}
}
