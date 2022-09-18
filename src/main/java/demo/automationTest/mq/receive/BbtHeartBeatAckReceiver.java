package demo.automationTest.mq.receive;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
import demo.automationTest.service.impl.AutomationTestCommonService;

@Component
@RabbitListener(queues = AutomationTestMQConstant.HEART_BEAT)
public class BbtHeartBeatAckReceiver extends AutomationTestCommonService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		constantService.setLastHeartBeat(LocalDateTime.now());
	}
}
