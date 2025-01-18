package demo.finance.cryptoCoin.trading.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderDTO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinBinanceFutureCmCancelOrderProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendCancleOrder(CryptoCoinBinanceFutureCmCancelOrderDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.BINANCE_CM_FUTURE_CANCEL_ORDER, json.toString());
	}

}