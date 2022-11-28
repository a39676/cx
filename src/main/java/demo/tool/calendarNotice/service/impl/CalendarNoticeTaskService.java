package demo.tool.calendarNotice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.tool.calendarNotice.pojo.type.CalendarNoticeTaskType;
import demo.tool.calendarNotice.service.CalendarNoticeService;

@Component
public class CalendarNoticeTaskService extends CommonTaskService {

	@Autowired
	private CalendarNoticeService calendarNoticeService;

//	@Scheduled(fixedDelay  = 1000L * 60 * 5)
	@Scheduled(fixedDelay  = 1000L * 5)
	public void findAndSendNoticeTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.CALENDAR_NOTICE);
		dto.setTaskId(snowFlake.getNextId());

		CalendarNoticeTaskType calendarNoticeTaskType = CalendarNoticeTaskType.FIND_AND_SEND_NOTICE;
		dto.setTaskSecondCode(calendarNoticeTaskType.getCode());
		dto.setTaskSecondName(calendarNoticeTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}

	public void findAndSendNotice() {
		calendarNoticeService.findAndSendNotice();
	}

	@Scheduled(fixedDelay = 1000L * 60 * 3)
	public void findAndSendStrongNoticeTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.CALENDAR_NOTICE);
		dto.setTaskId(snowFlake.getNextId());

		CalendarNoticeTaskType calendarNoticeTaskType = CalendarNoticeTaskType.FIND_AND_SNED_STRONG_NOTICE;
		dto.setTaskSecondCode(calendarNoticeTaskType.getCode());
		dto.setTaskSecondName(calendarNoticeTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}

	public void findAndSendStrongNotice() {
		calendarNoticeService.findAndSendStrongNotice();
	}

	@Scheduled(cron = "01 30 20 * * *")
	public void sendTomorrowNoticeListTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.CALENDAR_NOTICE);
		dto.setTaskId(snowFlake.getNextId());

		CalendarNoticeTaskType calendarNoticeTaskType = CalendarNoticeTaskType.SEND_TOMORROW_NOTICE_LIST;
		dto.setTaskSecondCode(calendarNoticeTaskType.getCode());
		dto.setTaskSecondName(calendarNoticeTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}

	public void sendTomorrowNoticeList() {
		calendarNoticeService.sendTomorrowNoticeList();
	}

}
