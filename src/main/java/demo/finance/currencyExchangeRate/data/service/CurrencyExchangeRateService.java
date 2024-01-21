package demo.finance.currencyExchangeRate.data.service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

public interface CurrencyExchangeRateService {

	void sendDailyDataQuery();

	CommonResult receiveDailyData(CurrencyExchageRateCollectResult inputDataResult);

	void sendDataQuery(Boolean isDailyQuery);

	Double getRate(CurrencyType from, CurrencyType to);

}
