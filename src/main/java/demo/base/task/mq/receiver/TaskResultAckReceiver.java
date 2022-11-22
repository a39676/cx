package demo.base.task.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.task.pojo.constant.TaskMqConstant;
import demo.base.task.service.TaskHandlerService;
import demo.common.service.CommonService;

@Component
@RabbitListener(queues = TaskMqConstant.TASK_INSERT_QUEUE)
public class TaskResultAckReceiver extends CommonService {

	@Autowired
	private TaskHandlerService taskHandlerService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CommonResult result = taskHandlerService.startEvent(messageStr);
			if (result.isSuccess()) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			} else {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
			}
		} catch (Exception e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}

}
