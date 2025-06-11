package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.ORDER_RESULT_FEEDBACK)
public class CryptoCoinOrderResultFeedbackReceiver extends CommonMessageQueueReceiverService {

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, messageStr,
					TelegramStaticChatID.MY_ID);
		} catch (Exception e) {
			log.error("mq error, " + CryptoCoinMQConstant.ORDER_RESULT_FEEDBACK + ", e:" + e.getLocalizedMessage());
		}
	}
}
