package demo.automationTest.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.automationTest.pojo.type.AutomationTestTaskType;
import demo.automationTest.service.AutomationTestReportService;
import demo.automationTest.service.TestEventService;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
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
	
	
	@Scheduled(cron="0 2 3 * * *")
	public void sendDeleteOldDataTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.AUTOMATION_TEST);
		dto.setTaskId(snowFlake.getNextId());
		
		AutomationTestTaskType subTaskType = AutomationTestTaskType.DELETE_OLD_DATA;
		dto.setTaskSecondCode(subTaskType.getCode());
		dto.setTaskSecondName(subTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}
	
	public void deleteOldData() {
		reportService.deleteOldData(null);
	}
	
	@Scheduled(cron="0 */1 * * * ?")
	public void sendTestEventToRunTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.AUTOMATION_TEST);
		AutomationTestTaskType subTaskType = AutomationTestTaskType.SEND_TEST_EVENT_TO_RUN;
		
		dto.setTaskId(snowFlake.getNextId());
		dto.setTaskSecondCode(subTaskType.getCode());
		dto.setTaskSecondName(subTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}
	
	public void sendTestEventToRun() {
		testEventService.sendTestEventToRun();
	}
	
	@Scheduled(cron="0 0 */1 * * ?")
	public void sendHandleLongWaitingEventTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.AUTOMATION_TEST);
		AutomationTestTaskType subTaskType = AutomationTestTaskType.HANDLE_LONG_WAITING_EVENT;
		
		dto.setTaskId(snowFlake.getNextId());
		dto.setTaskSecondCode(subTaskType.getCode());
		dto.setTaskSecondName(subTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}
	
	public void handleLongWaitingEvent() {
		testEventService.handleLongWaitingEvent();
	}
}
