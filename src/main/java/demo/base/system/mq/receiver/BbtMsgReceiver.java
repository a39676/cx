package demo.base.system.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import auxiliaryCommon.pojo.constant.ServiceMQConstant;
import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import demo.common.service.CommonMessageQueueReceiverService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Component
@RabbitListener(queues = ServiceMQConstant.BBT_SEND_MESSAGE_QUEUE)
public class BbtMsgReceiver extends CommonMessageQueueReceiverService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			ServiceMsgDTO dto = new Gson().fromJson(messageStr, ServiceMsgDTO.class);
			telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE, dto.getMsg(), TelegramStaticChatID.MY_ID);
		} catch (Exception e) {
			log.error("mq error, " + ServiceMQConstant.BBT_SEND_MESSAGE_QUEUE + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
		}
	}
}
