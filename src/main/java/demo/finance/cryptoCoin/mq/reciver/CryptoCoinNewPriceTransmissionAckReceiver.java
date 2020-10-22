package demo.finance.cryptoCoin.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import demo.finance.cryptoCoin.service.CryptoCoinPriceService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinNewPriceDTO;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_CONI_NEW_PRICE_DATA)
public class CryptoCoinNewPriceTransmissionAckReceiver {

	@Autowired
	private CryptoCoinPriceService cryptoCoinService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinNewPriceDTO dto = new Gson().fromJson(messageStr, CryptoCoinNewPriceDTO.class);
			cryptoCoinService.reciveCoinNewPrice(dto);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
			e.printStackTrace();
		}
	}
}
