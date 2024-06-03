package demo.base.system.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import demo.common.service.CommonMessageQueueReceiverService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.NORMAL_MESSAGE_QUEUE)
public class CryptoCoinNormalMsgReceiver extends CommonMessageQueueReceiverService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			ServiceMsgDTO dto = new Gson().fromJson(messageStr, ServiceMsgDTO.class);
			telegramService.sendMessageByChatRecordId(TelegramBotType.NORMAL_MSG, dto.getMsg(),
					TelegramStaticChatID.MY_ID);
		} catch (Exception e) {
			log.error("mq error, " + CryptoCoinMQConstant.NORMAL_MESSAGE_QUEUE + ", e:"
					+ e.getLocalizedMessage());
			log.error(messageStr);
		}
	}
}
