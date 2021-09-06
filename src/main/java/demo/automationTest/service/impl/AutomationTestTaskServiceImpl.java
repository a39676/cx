package demo.automationTest.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.tool.other.service.ComplexToolService;

@Component
public class AutomationTestTaskServiceImpl extends AutomationTestCommonService {

	@Autowired
	private ComplexToolService complexToolService;
	
	@Scheduled(cron="0 */10 * * * ?")
	public void checkBbtHeartBeat() {
		LocalDateTime heartBeatTime = constantService.getLastHeartBeat();
		if(heartBeatTime == null) {
			complexToolService.notificationBbtDown();
		}
		long minutes = ChronoUnit.MINUTES.between(heartBeatTime, LocalDateTime.now());
		if(minutes > 10) {
			complexToolService.notificationBbtDown();
		}
	}
}
