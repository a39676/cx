package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPriceCommonData;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice5minute> getData(CryptoCoinType coinType, CurrencyType currencyType, Integer minutes);

	List<CryptoCoinPriceCommonData> getCommonData(CryptoCoinType coinType, CurrencyType currencyType, Integer minutes);

}
