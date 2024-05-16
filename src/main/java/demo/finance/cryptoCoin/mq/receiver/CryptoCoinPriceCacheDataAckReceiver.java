// 2024-04-30 准备废弃, 相关数据监控逐步迁移到专用项目
//package demo.finance.cryptoCoin.mq.receiver;
//
//import java.io.IOException;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.rabbitmq.client.Channel;
//
//import demo.common.service.CommonMessageQueueReceiverService;
//import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
//import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
//import telegram.pojo.constant.TelegramStaticChatID;
//import telegram.pojo.type.TelegramBotType;
//
//@Component
//@RabbitListener(queues = "")
//public class CryptoCoinPriceCacheDataAckReceiver extends CommonMessageQueueReceiverService {
//
//	@Autowired
//	private CryptoCoinPriceCacheService cryptoCoinPriceCacheService;
//
//	@RabbitHandler
//	public void process(String messageStr, Channel channel, Message message) throws IOException {
//		try {
//			CryptoCoinPriceCommonDataBO bo = buildObjFromJsonCustomization(messageStr,
//					CryptoCoinPriceCommonDataBO.class);
//			cryptoCoinPriceCacheService.reciveData(bo);
//		} catch (Exception e) {
//			log.error("mq error, " + "[queue name]" + ", e:" + e.getLocalizedMessage());
//			log.error(messageStr);
//			telegramService.sendMessageByChatRecordId(TelegramBotType.CRYPTO_COIN_LOW_PRICE_NOTICE_BOT,
//					"Crypto daily data error: " + e.getLocalizedMessage() + ", msgStr: " + messageStr,
//					TelegramStaticChatID.MY_ID);
//		}
//	}
//}
