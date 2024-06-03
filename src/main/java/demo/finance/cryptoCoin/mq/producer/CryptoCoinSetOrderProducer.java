package demo.finance.cryptoCoin.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;

@Component
public class CryptoCoinSetOrderProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendCryptoCoinDailyDataQueryForTest(String str) {
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.SET_ORDER, str);
	}

}