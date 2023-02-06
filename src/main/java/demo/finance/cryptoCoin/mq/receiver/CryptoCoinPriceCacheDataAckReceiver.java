package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_COIN_PRICE_CACHE_QUEUE)
public class CryptoCoinPriceCacheDataAckReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private CryptoCoinPriceCacheService cryptoCoinPriceCacheService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinPriceCommonDataBO bo = buildObjFromJsonCustomization(messageStr, CryptoCoinPriceCommonDataBO.class);
			cryptoCoinPriceCacheService.reciveData(bo);
		} catch (Exception e) {
			log.error("mq error, " + CryptoCoinMQConstant.CRYPTO_COIN_PRICE_CACHE_QUEUE + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT, "Crypto daily data error: " + e.getLocalizedMessage() + ", msgStr: " + messageStr, TelegramStaticChatID.MY_ID);
		}
	}
}
