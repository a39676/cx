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
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = { ServiceMQConstant.NOTICE_MSG_1_GROUP })
public class CryptoCoinNoticeMsg1GroupMsgReceiver extends CommonMessageQueueReceiverService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			ServiceMsgDTO dto = new Gson().fromJson(messageStr, ServiceMsgDTO.class);
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT, dto.getMsg(),
					TelegramStaticChatID.NOTICE_MSG_1_GROUP_ID);
		} catch (Exception e) {
			log.error("mq error, " + ServiceMQConstant.NOTICE_MSG_1_GROUP + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
		}
	}
}
