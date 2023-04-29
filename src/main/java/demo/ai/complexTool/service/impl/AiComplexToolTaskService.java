package demo.ai.complexTool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.ai.aiArt.service.AiArtCommonService;
import demo.ai.complexTool.service.AiComplexToolService;

@Component
public class AiComplexToolTaskService extends AiArtCommonService {

	@Autowired
	private AiComplexToolService complexToolService;

	@Scheduled(cron = "06 09 * * * *")
	public void deleteParameterFile() {
		complexToolService.setRecharMarkForAdmin();
	}

}
