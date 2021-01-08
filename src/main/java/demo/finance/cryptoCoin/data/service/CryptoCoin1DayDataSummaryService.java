package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin1DayDataSummaryService {

	CommonResult summaryHistoryData();

	List<CryptoCoinPrice1day> getData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

}