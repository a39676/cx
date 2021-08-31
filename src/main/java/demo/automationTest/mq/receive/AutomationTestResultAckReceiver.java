package demo.automationTest.mq.receive;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import autoTest.testEvent.pojo.constant.AutomationTestResultMQConstant;
import demo.automationTest.service.AutomationTestResultReceiveService;
import demo.common.service.CommonService;

@Component
@RabbitListener(queues = AutomationTestResultMQConstant.AUTOMATION_TEST_RESULT_QUEUE)
public class AutomationTestResultAckReceiver extends CommonService {

	@Autowired
	private AutomationTestResultReceiveService reportReceiveService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			reportReceiveService.handleAutomationTestResult(messageStr);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
	
	
}
