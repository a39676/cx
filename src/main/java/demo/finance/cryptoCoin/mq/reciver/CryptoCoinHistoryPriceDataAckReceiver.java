package demo.finance.cryptoCoin.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_CONI_HISTORY_PRICE_DATA)
public class CryptoCoinHistoryPriceDataAckReceiver {

	@Autowired
	private CryptoCoin1MinuteDataSummaryService cryptoCoin1MinuteDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinHistoryPriceDTO dto = new Gson().fromJson(messageStr, CryptoCoinHistoryPriceDTO.class);
			cryptoCoin1MinuteDataService.reciveCoinHistoryPrice(dto);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
