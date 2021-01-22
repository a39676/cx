package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1month;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin1MonthDataSummaryService {

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice1month> getData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonData(
			CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataFillWithCache(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);


}
