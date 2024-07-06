package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import demo.finance.cryptoCoin.data.service.CryptoCoinIndexDataService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.INDEX_DATA)
public class CryptoCoinIndexDataReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private CryptoCoinIndexDataService cryptoCoinIndexDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinPriceCommonDataBO dto = buildObjFromJsonCustomization(messageStr,
					CryptoCoinPriceCommonDataBO.class);
			cryptoCoinIndexDataService.receiveData(dto);
		} catch (Exception e) {
			log.error("mq error, " + CryptoCoinMQConstant.INDEX_DATA + ", e:" + e.getLocalizedMessage());
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT,
					"Crypto index data error: " + e.getLocalizedMessage() + ", msgStr: " + messageStr,
					TelegramStaticChatID.MY_ID);
		}
	}
}
