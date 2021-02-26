package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin1DayDataSummaryService {

	/**
	 * 2021-02-26
	 * 将停用, 改直接从api 获取日线数据
	 */
	CommonResult summaryHistoryData();

	List<CryptoCoinPrice1day> getData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataFillWithCache(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	CommonResult reciveDailyData(CryptoCoinDataDTO dto);

}
