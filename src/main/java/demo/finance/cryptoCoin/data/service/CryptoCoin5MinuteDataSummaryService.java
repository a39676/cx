package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice5minute> getDataList(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

}
