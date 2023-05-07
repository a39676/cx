package demo.ai.aiArt.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.aiArt.pojo.constant.AiArtMqConstant;
import demo.common.service.CommonService;

@Component
public class AiArtJobQueueSettingProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String booleanInt) {
		rabbitTemplate.convertAndSend(AiArtMqConstant.AI_ART_JOB_QUEUE_SETTING, booleanInt);
	}

}
