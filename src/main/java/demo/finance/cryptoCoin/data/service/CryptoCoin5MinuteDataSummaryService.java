package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice5minute;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult deleteExpiredCacheData();

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice5minute> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime);

}
