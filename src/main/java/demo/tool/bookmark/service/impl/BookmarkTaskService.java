package demo.tool.bookmark.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.tool.bookmark.pojo.type.BookmarkTaskType;
import demo.tool.bookmark.service.BookmarkService;

@Component
public class BookmarkTaskService extends CommonTaskService {

	@Autowired
	private BookmarkService service;

	@Scheduled(cron = "30 29 03 * * *")
	public void sendDeleteOldDataTask() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.BOOKMARK);
		dto.setTaskId(snowFlake.getNextId());
		
		BookmarkTaskType subTaskType = BookmarkTaskType.RE_BALANCE_WEIGHT;
		dto.setTaskSecondCode(subTaskType.getCode());
		dto.setTaskSecondName(subTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}
	
	public void reBalanceWeight() {
		service.reBalanceWeight();
		service.cleanOldFile();
	}
}
