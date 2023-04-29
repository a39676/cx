package demo.ai.aiArt.mq.reciver;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import ai.aiArt.pojo.constant.AiArtMqConstant;
import demo.ai.aiArt.service.impl.AiArtCacheService;
import demo.common.service.CommonService;

@Component
@RabbitListener(queues = AiArtMqConstant.AI_ART_AUTOMATIC_HEART_BEAT)
public class AutomaticHeartBeatAckReceiver extends CommonService {

	@Autowired
	private AiArtCacheService cacheService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		cacheService.setIsRunning(true);
		cacheService.setLastHearBeatTime(LocalDateTime.now());
	}
}
