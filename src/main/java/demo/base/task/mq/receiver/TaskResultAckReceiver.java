package demo.base.task.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.base.task.pojo.constant.TaskMqConstant;
import demo.base.task.service.TaskHandlerService;
import demo.common.service.CommonMessageQueueReceiverService;

@Component
@RabbitListener(queues = TaskMqConstant.TASK_INSERT_QUEUE)
public class TaskResultAckReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private TaskHandlerService taskHandlerService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		taskHandlerService.startEvent(messageStr);
	}
}
