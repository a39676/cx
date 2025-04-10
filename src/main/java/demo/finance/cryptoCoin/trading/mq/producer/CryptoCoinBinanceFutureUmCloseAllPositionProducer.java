package demo.finance.cryptoCoin.trading.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.binance.future.pojo.dto.CryptoCoinBinanceFutureCloseAllPositionDTO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinBinanceFutureUmCloseAllPositionProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendCloseAllPosition(CryptoCoinBinanceFutureCloseAllPositionDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.BINANCE_UM_FUTURE_CLOSE_ALL_POSITION, json.toString());
	}

}