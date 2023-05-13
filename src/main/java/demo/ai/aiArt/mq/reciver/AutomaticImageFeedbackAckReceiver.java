package demo.ai.aiArt.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import ai.aiArt.pojo.constant.AiArtMqConstant;
import demo.ai.aiArt.service.AiArtService;
import demo.common.service.CommonService;

@Component
@RabbitListener(queues = AiArtMqConstant.AI_ART_IMAGE_FEEDBACK)
public class AutomaticImageFeedbackAckReceiver extends CommonService {

	@Autowired
	private AiArtService aiArtService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		aiArtService.receiveImgJobResultForMQ(messageStr);
	}
}
