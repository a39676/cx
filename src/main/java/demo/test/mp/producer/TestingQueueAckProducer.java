package demo.test.mp.producer;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import demo.test.mp.constant.TestingMQConstant;
import net.sf.json.JSONObject;

@Component
public class TestingQueueAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send() {
		log.error("MQ testing");
		JSONObject json = new JSONObject();
		json.put("testKey", LocalDateTime.now().toString());
		rabbitTemplate.convertAndSend(TestingMQConstant.QUEUE_CHANNEL_NAME, json.toString());
		log.error("MQ testing sended");
	}


}
