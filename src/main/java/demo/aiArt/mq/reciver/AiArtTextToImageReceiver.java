package demo.aiArt.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.aiArt.pojo.constant.AiArtMqConstant;
import demo.aiArt.service.AiArtService;
import demo.common.service.CommonService;

@Component
@RabbitListener(queues = AiArtMqConstant.AI_ART_TEXT_TO_IMAGE)
public class AiArtTextToImageReceiver extends CommonService {

	@Autowired
	private AiArtService aiArtService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			Long jobId = Long.parseLong(messageStr);
			aiArtService.txtToImgByJobId(jobId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
