package demo.test.mp.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonService;
import demo.test.mp.constant.TestingMQConstant;

@Component
@RabbitListener(queues = TestingMQConstant.QUEUE_CHANNEL_NAME)
public class TestingQueueAckReceiver extends CommonService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			log.error("receive msg from: " + TestingMQConstant.QUEUE_CHANNEL_NAME + ": " + messageStr);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			log.error("mq error, " + TestingMQConstant.QUEUE_CHANNEL_NAME + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
