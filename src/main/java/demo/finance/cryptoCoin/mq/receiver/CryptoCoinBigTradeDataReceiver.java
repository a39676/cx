package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import demo.finance.cryptoCoin.data.service.CryptoCoinBigTradeDataService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.BIG_TRADE_DATA)
public class CryptoCoinBigTradeDataReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private CryptoCoinBigTradeDataService cryptoCoinBigTradeDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			cryptoCoinBigTradeDataService.receiveNewBigTradeFutureUmDataMessage(messageStr);
		} catch (Exception e) {
			log.error("MQ error, " + CryptoCoinMQConstant.BIG_TRADE_DATA + ", e:" + e.getLocalizedMessage());
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT,
					"Crypto big move (future UM) data error: " + e.getLocalizedMessage() + ", msgStr: " + messageStr,
					TelegramStaticChatID.MY_ID);
		}
	}
}
