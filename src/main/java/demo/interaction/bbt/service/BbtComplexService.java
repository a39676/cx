package demo.interaction.bbt.service;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import finance.cnStockMarket.pojo.dto.CnStockMarketDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;
import net.sf.json.JSONObject;

public interface BbtComplexService {

	CommonResult textMessageForwarding(ServiceMsgDTO dto);

	CommonResult receiveCurrencyExchangeRateDailyData(CurrencyExchageRateCollectResult dto);

	CommonResult receiveCnStockMarketData(CnStockMarketDataDTO dto);

	JSONObject getCryptoCoinOption(BaseStrDTO dto);

	void makeSureWorkerClong1Alive();

	CommonResult workerClone1IsAlive(BaseStrDTO dto);

}
