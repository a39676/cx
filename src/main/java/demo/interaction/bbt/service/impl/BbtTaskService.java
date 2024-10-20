package demo.interaction.bbt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.interaction.bbt.service.BbtComplexService;

@Component
public class BbtTaskService {

	@Autowired
	private BbtComplexService bbtComplexService;

	@Scheduled(cron = "0 * * * * ?")
	public void checkBbtIsAlive() {
		bbtComplexService.checkBbtIsAlive();
	}
}
