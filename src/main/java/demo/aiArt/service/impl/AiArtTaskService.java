package demo.aiArt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.aiArt.service.AiArtService;
import demo.common.service.ToolCommonService;
import demo.test.controller.TestController2;

@Component
public class AiArtTaskService extends ToolCommonService {

	@Autowired
	private TestController2 t;
	@Autowired
	private AiArtService aiArtService;

	@Scheduled(fixedDelay = 1000L * 60 * 2)
	public void rerun() {
		aiArtService.rerun();
	}

	@Scheduled(fixedDelay = 1000L * 60 * 2)
	public void insertJobToKeepLive() {
		if (!systemOptionService.isDev()) {
			t.t4();
		}
	}

	@Scheduled(cron = "06 05 04 * * *")
	public void deleteParameterFile() {
		aiArtService.deleteParameterFile();
	}

}
