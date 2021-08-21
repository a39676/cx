package demo.automationTest.mq.receive;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import autoTest.testEvent.pojo.constant.AutomationTestResultMQConstant;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
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
			AutomationTestResultDTO bo = new Gson().fromJson(messageStr, AutomationTestResultDTO.class);
			reportReceiveService.savingReport(bo);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			log.error("mq error, " + AutomationTestResultMQConstant.AUTOMATION_TEST_RESULT_QUEUE + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
