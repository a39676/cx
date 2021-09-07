package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice5minute> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

}
