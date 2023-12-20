package demo.interaction.bbt.service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

public interface BbtComplexService {

	CommonResult textMessageForwarding(ServiceMsgDTO dto);

	CommonResult receiveCurrencyExchangeRateDailyData(CurrencyExchageRateCollectResult dto);

}
