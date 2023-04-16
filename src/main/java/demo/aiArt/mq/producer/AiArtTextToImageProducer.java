package demo.aiArt.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.aiArt.pojo.constant.AiArtMqConstant;
import demo.common.service.CommonService;

@Component
public class AiArtTextToImageProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(Long jobId) {
		if (jobId == null) {
			return;
		}
		rabbitTemplate.convertAndSend(AiArtMqConstant.AI_ART_TEXT_TO_IMAGE, String.valueOf(jobId));
	}

}
