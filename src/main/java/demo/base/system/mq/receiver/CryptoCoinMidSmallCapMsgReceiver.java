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
@RabbitListener(queues = ServiceMQConstant.CRYPTO_COIN_MID_SMALL_CAPITALIZATION_MSG)
public class CryptoCoinMidSmallCapMsgReceiver extends CommonMessageQueueReceiverService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			ServiceMsgDTO dto = new Gson().fromJson(messageStr, ServiceMsgDTO.class);
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT, dto.getMsg(),
					TelegramStaticChatID.MY_ID);
		} catch (Exception e) {
			log.error("mq error, " + ServiceMQConstant.CRYPTO_COIN_MID_SMALL_CAPITALIZATION_MSG + ", e:"
					+ e.getLocalizedMessage());
			log.error(messageStr);
		}
	}
}
