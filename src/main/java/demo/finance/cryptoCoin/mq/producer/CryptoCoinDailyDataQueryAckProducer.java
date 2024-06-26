package demo.finance.cryptoCoin.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinDailyDataQueryAckProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendCryptoCoinDailyDataQueryForTest(CryptoCoinDailyDataQueryDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.DAILY_DATA_QUERY, json.toString());
	}

}