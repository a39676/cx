package demo.pmemo.service.impl;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.pmemo.pojo.type.UrgeNoticeTaskType;
import demo.pmemo.service.UrgeNoticeService;

@Component
public class UrgeNoticeTaskService extends CommonTaskService {

	@Autowired
	private UrgeNoticeService urgeNoticeService;
	
	@Scheduled(fixedRate = 1000L * 60 * 60)
	public void sendUrgeNoticeTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.URGE_NOTICE);
		dto.setTaskId(snowFlake.getNextId());
		
		UrgeNoticeTaskType subTaskType = UrgeNoticeTaskType.SEND_URGE_NOTICE;
		dto.setTaskSecondCode(subTaskType.getCode());
		dto.setTaskSecondName(subTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}
	
	public void sendUrgeNotice() {
		LocalTime now = LocalTime.now();
		if(now.getHour() < 8 || now.getHour() > 22) {
			return;
		}
		urgeNoticeService.sendAllUrgeNoticeList();
	}
}
