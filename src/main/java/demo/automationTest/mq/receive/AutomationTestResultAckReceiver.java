package demo.automationTest.mq.receive;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import autoTest.testEvent.common.pojo.constant.AutomationTestMQConstant;
import demo.automationTest.service.AutomationTestResultReceiveService;
import demo.common.service.CommonMessageQueueReceiverService;

@Component
@RabbitListener(queues = AutomationTestMQConstant.AUTOMATION_TEST_RESULT_QUEUE)
public class AutomationTestResultAckReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private AutomationTestResultReceiveService reportReceiveService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		reportReceiveService.handleAutomationTestResult(messageStr);
	}
	
	
}
