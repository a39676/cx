package demo.finance.cryptoCoin.trading.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionOrderCommonDTO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinBinanceFutureUmChangeToMaxLeverageProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendChangeToMaxLeverage(CryptoCoinInteractionOrderCommonDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.BINANCE_UM_FUTURE_CHANGE_TO_MAX_LEVERAGE, json.toString());
	}

}