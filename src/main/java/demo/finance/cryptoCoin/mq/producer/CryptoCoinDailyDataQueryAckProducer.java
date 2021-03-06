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

	public void send(CryptoCoinDailyDataQueryDTO dto) {
		if (dto == null) {
			return;
		}
		JSONObject json = dtoToJSON(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_DAILY_DATA_QUERY, json.toString());
	}

	private JSONObject dtoToJSON(CryptoCoinDailyDataQueryDTO dto) {
		JSONObject json = new JSONObject();

		if (dto.getCoinName() != null) {
			json.put("coinName", dto.getCoinName());
		}
		if (dto.getCounting() != null) {
			json.put("counting", dto.getCounting());
		}
		

		return json;
	}

}
