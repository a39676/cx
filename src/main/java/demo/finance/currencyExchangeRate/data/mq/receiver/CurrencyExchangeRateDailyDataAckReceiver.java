package demo.finance.currencyExchangeRate.data.mq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import demo.common.service.CommonService;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;
import finance.currencyExchangeRate.pojo.constant.CurrencyExchangeRateMQConstant;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

@Component
@RabbitListener(queues = CurrencyExchangeRateMQConstant.CURRENCY_EXCHANGE_RATE_DAILY_DATA)
public class CurrencyExchangeRateDailyDataAckReceiver extends CommonService {

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRate1DayDataService;

	@RabbitHandler
	public void process(String messageStr, Channel channel, Message message) throws IOException {
		try {
			CurrencyExchageRateCollectResult dto = new Gson().fromJson(messageStr, CurrencyExchageRateCollectResult.class);
			currencyExchangeRate1DayDataService.receiveDailyData(dto);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			log.error("mq error, " + CurrencyExchangeRateMQConstant.CURRENCY_EXCHANGE_RATE_DAILY_DATA + ", e:" + e.getLocalizedMessage());
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
		}
	}
}
