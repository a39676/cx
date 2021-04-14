package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;

public interface CryptoCoin1MinuteDataSummaryService {

	CommonResult reciveMinuteData(CryptoCoinDataDTO dto);

	CommonResult deleteExpiredCacheData();

	List<CryptoCoinPrice1minute> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	/**
	 * 2020-12-24
	 * 暂时只合并处理最近5分钟的数据
	 */
	void mergeDuplicateData();

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime);

	void summaryLowPriceRedisData();

}
