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
import demo.common.service.HeartBeatService;
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
	private HeartBeatService heartBeatService;

//	@Scheduled(fixedDelay = 1000L * 120)
	// TODO 2025 12 05
	public void checkHeartBeat() {
		LocalDateTime heartBeatTime = null;
		for (HeartBeatType heartBeatType : HeartBeatType.values()) {
			heartBeatTime = heartBeatService.getHeartBeatMap().get(heartBeatType.getName());
			sendServiceDownNotificationIfNecessary(heartBeatTime, heartBeatType);
		}
	}

	private void sendServiceDownNotificationIfNecessary(LocalDateTime heartBeatTime, HeartBeatType heartBeatType) {
		int maxHeartBeatGap = 10;
		if (heartBeatTime == null) {
			complexToolService.notificationServiceDown(heartBeatType);
			return;
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
