package demo.finance.cryptoCoin.trading.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.common.service.CommonService;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.pojo.constant.CryptoCoinMQConstant;
import net.sf.json.JSONObject;

@Component
public class CryptoCoinBinanceUmBtcArbitrageWithBatchProducer extends CommonService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void binanceUmBtcArbitrageWithBatch(CryptoCoinBinanceBtArbitrageWithBatchDTO dto) {
		JSONObject json = JSONObject.fromObject(dto);
		rabbitTemplate.convertAndSend(CryptoCoinMQConstant.BINANCE_UM_BTC_ARBITRAGE_WITH_BATCH, json.toString());
	}

}