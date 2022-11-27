package demo.joy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.pojo.dto.SendTaskDTO;
import demo.base.task.pojo.type.TaskType;
import demo.base.task.service.CommonTaskService;
import demo.joy.common.pojo.type.JoyTaskType;
import demo.joy.garden.service.JoyGradenInfoService;

@Component
public class JoyTaskService extends CommonTaskService {

	@Autowired
	private JoyGradenInfoService infoService;

	@Scheduled(cron = "* */30 * * * ?")
	public void cacheToDatabase() {
		SendTaskDTO dto = new SendTaskDTO();
		dto.setFirstTask(TaskType.JOY);
		dto.setTaskId(snowFlake.getNextId());
		
		JoyTaskType articleTaskType = JoyTaskType.CACHE_TO_DATABASE;
		dto.setTaskSecondCode(articleTaskType.getCode());
		dto.setTaskSecondName(articleTaskType.getName());
		
		taskInsertAckProducer.send(dto);
	}
	
	public void cacheToDatabaseTask() {
		infoService.cacheToDatabase();
	}

}
