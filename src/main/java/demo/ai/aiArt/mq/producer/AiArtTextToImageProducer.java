package demo.ai.aiArt.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.aiArt.pojo.constant.AiArtMqConstant;
import demo.ai.aiArt.service.AiArtCommonService;
import net.sf.json.JSONObject;

@Component
public class AiArtTextToImageProducer extends AiArtCommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(JSONObject parameterInJson) {
		if (parameterInJson == null) {
			return;
		}
		addJobInQueueMark(parameterInJson.getLong("jobId"));
		rabbitTemplate.convertAndSend(AiArtMqConstant.AI_ART_TEXT_TO_IMAGE, parameterInJson.toString());
	}

}
