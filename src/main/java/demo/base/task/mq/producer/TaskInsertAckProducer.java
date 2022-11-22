package demo.base.task.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.base.task.pojo.constant.TaskMqConstant;
import demo.base.task.pojo.dto.SendTaskDTO;
import demo.common.service.CommonService;
import net.sf.json.JSONObject;

@Component
public class TaskInsertAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(SendTaskDTO dto) {
		if (dto == null) {
			return;
		}
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(TaskMqConstant.TASK_INSERT_QUEUE, json.toString());
	}

}