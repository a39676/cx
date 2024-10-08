package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import demo.finance.cryptoCoin.data.service.CryptoCoinForceOrderDataService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.FORCE_ORDER_DATA)
public class CryptoCoinForceOrderDataReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private CryptoCoinForceOrderDataService cryptoCoinForceOrderDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			cryptoCoinForceOrderDataService.receiveNewForceOrderFutureUmDataMessage(messageStr);
		} catch (Exception e) {
			log.error("MQ error, " + CryptoCoinMQConstant.FORCE_ORDER_DATA + ", e:" + e.getLocalizedMessage());
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT,
					"Crypto big force order (future UM) data error: " + e.getLocalizedMessage() + ", msgStr: "
							+ messageStr,
					TelegramStaticChatID.MY_ID);
		}
	}
}
