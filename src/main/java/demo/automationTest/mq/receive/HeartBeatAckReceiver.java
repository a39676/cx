package demo.automationTest.mq.receive;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import auxiliaryCommon.pojo.constant.ServiceMQConstant;
import auxiliaryCommon.pojo.type.HeartBeatType;
import demo.automationTest.service.impl.AutomationTestCommonService;

@Component
@RabbitListener(queues = ServiceMQConstant.HEART_BEAT)
public class HeartBeatAckReceiver extends AutomationTestCommonService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		System.out.println("Debug: " + message);
		HeartBeatType heartBeatType = HeartBeatType.getType(String.valueOf(message));
		if (HeartBeatType.BBT.equals(heartBeatType)) {
			constantService.setBbtLastHeartBeat(LocalDateTime.now());
		} else if (HeartBeatType.WORKER1.equals(heartBeatType)) {
			constantService.setWorkerLastHeartBeat();
		}
	}
}
