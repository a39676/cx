package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinPriceCommonDataBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin1MinuteDataSummaryService {

	CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto);

	CommonResult deleteExpiredCacheData();

	List<CryptoCoinPrice1minute> getData(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	/**
	 * 2020-12-24
	 * 暂时只合并处理最近5分钟的数据
	 */
	void mergeDuplicateData();

	List<CryptoCoinPriceCommonDataBO> getCommonDataFillWithCache(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

}
