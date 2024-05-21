package demo.automationTest.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import auxiliaryCommon.pojo.type.HeartBeatType;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.TestEventService;
import demo.base.task.service.CommonTaskService;
import demo.tool.other.service.ComplexToolService;

@Component
public class AutomationTestTaskServiceImpl extends CommonTaskService {

	@Autowired
	private ComplexToolService complexToolService;
	@Autowired
	private TestEventService testEventService;
	@Autowired
	private AutomationTestReportService reportService;
	@Autowired
	private AutomationTestConstantService constantService;

	@Scheduled(fixedDelay = 1000L * 120)
	public void checkHeartBeat() {
		LocalDateTime heartBeatTime = constantService.getBbtLastHeartBeat();
		sendServiceDownNotificationIfNecessary(heartBeatTime, HeartBeatType.BBT);

		heartBeatTime = constantService.getWorker1LastHeartBeat();
		sendServiceDownNotificationIfNecessary(heartBeatTime, HeartBeatType.WORKER1);

		heartBeatTime = constantService.getMonitorLastHeartBeat();
		sendServiceDownNotificationIfNecessary(heartBeatTime, HeartBeatType.MONITOR);
	}

	private void sendServiceDownNotificationIfNecessary(LocalDateTime heartBeatTime, HeartBeatType heartBeatType) {
		int maxHeartBeatGap = 10;
		if (heartBeatTime == null) {
			complexToolService.notificationServiceDown(heartBeatType);
		}
		long minutes = ChronoUnit.MINUTES.between(heartBeatTime, LocalDateTime.now());
		if (minutes > maxHeartBeatGap) {
			complexToolService.notificationServiceDown(heartBeatType);
		}
	}

	@Scheduled(cron = "0 2 3 * * *")
	public void deleteOldData() {
		reportService.deleteOldData(null);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void sendTestEventToRun() {
		testEventService.sendTestEventToRun();
	}

	@Scheduled(cron = "0 0 */1 * * ?")
	public void handleLongWaitingEvent() {
		testEventService.handleLongWaitingEvent();
	}
}
