package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice5minute> getData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataFillWithCache(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

}
