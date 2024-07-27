package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonMessageQueueReceiverService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.MINUTE_DATA)
public class CryptoCoinMinuteDataReceiver extends CommonMessageQueueReceiverService {

	@Autowired
	private CryptoCoin1MinuteDataSummaryService cryptoCoin1MinuteDataSummaryService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinDataDTO dto = buildObjFromJsonCustomization(messageStr, CryptoCoinDataDTO.class);
			cryptoCoin1MinuteDataSummaryService.reciveMinuteData(dto);
		} catch (Exception e) {
			log.error("mq error, " + CryptoCoinMQConstant.MINUTE_DATA + ", e:" + e.getLocalizedMessage());
			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT,
					"Crypto minute data error: " + e.getLocalizedMessage() + ", msgStr: " + messageStr,
					TelegramStaticChatID.MY_ID);
		}
	}
}
