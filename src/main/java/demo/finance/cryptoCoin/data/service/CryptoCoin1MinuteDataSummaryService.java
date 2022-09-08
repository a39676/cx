package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

public interface CryptoCoin1MinuteDataSummaryService {

	CommonResult reciveMinuteData(CryptoCoinDataDTO dto);

	CommonResult deleteExpiredCacheData();

	List<CryptoCoinPrice1minute> getDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime);

	/**
	 * 2020-12-24
	 * 暂时只合并处理最近5分钟的数据
	 */
	void mergeDuplicateData();

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime startTime);

	CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyTypeForCryptoCoin currencyType,
			LocalDateTime datetime);

	void summaryLowPriceRedisData();

}
