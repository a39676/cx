package demo.interaction.bbt.service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

public interface BbtComplexService {

	CommonResult textMessageForwarding(ServiceMsgDTO dto);

	CommonResult receiveCurrencyExchangeRateDailyData(CurrencyExchageRateCollectResult dto);

	CommonResult receiveCnStockMarketData(CnStockMarketDataDTO dto);

	CommonResult getBbtIsAlive();

	void checkBbtIsAlive();

}
