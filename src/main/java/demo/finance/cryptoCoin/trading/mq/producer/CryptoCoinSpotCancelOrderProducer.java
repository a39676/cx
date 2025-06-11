package demo.finance.cryptoCoin.trading.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotCancelMultipleOrderDTO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinSpotCancelOrderProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void cancelMultipleOrder(CryptoCoinSpotCancelMultipleOrderDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.CRYPTO_COIN_SPOT_CANCEL_MULTIPLE_ORDER, json.toString());
	}

}