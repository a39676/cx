package demo.ai.aiArt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.ai.aiArt.service.AiArtService;
import demo.common.service.ToolCommonService;

@Component
public class AiArtTaskService extends ToolCommonService {

	@Autowired
	private AiArtService aiArtService;
	@Autowired
	private AiArtOptionService aiArtOptionService;
	@Autowired
	private AiArtColabUtil aiArtColabUtil;

	@Scheduled(fixedDelay = 1000L * 60 * 2)
	public void rerun() {
		aiArtService.rerun();
	}

	@Scheduled(fixedDelay = 1000L * 60 * 5)
	public void insertJobToKeepLive() {
		if (aiArtOptionService.getIsRunning()) {
			aiArtColabUtil.getModelList();
		}
	}

	@Scheduled(cron = "06 05 04 * * *")
	public void deleteParameterFile() {
		aiArtService.deleteParameterFile();
	}

}
