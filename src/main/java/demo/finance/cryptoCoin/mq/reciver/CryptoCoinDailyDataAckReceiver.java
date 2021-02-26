package demo.finance.cryptoCoin.mq.reciver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;

@Component
@RabbitListener(queues = CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA)
public class CryptoCoinDailyDataAckReceiver extends CommonService {

	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CryptoCoinDataDTO dto = new Gson().fromJson(messageStr, CryptoCoinDataDTO.class);
			cryptoCoin1DayDataService.reciveDailyData(dto);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			log.error("mq error, " + CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA + ", e:" + e.getLocalizedMessage());
			log.error(messageStr);
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
