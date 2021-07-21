package demo.finance.cryptoCoin.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_COIN_PRICE_CACHE_QUEUE)
public class CryptoCoinPriceCacheDataAckReceiver extends CommonService {

	@Autowired
	private CryptoCoinPriceCacheService cryptoCoinPriceCacheService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinPriceCommonDataBO bo = cryptoCoinPriceCacheService.dataStrToBO(messageStr);
			cryptoCoinPriceCacheService.reciveData(bo);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			log.error("mq error, " + CryptoCoinMQConstant.CRYPTO_COIN_PRICE_CACHE_QUEUE + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
