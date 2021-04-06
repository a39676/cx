package demo.finance.cryptoCoin.data.service;

import java.time.LocalDateTime;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinDataDTO;

public interface CryptoCoin1DayDataSummaryService {

	List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPrice1day> getDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime, LocalDateTime endTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinCatalog coinType,
			CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime startTime, LocalDateTime endTime);

	/** 为单币种刷新数据 */
	CommonResult receiveDailyData(CryptoCoinDataDTO dto);

	/** 为后续币种刷新数据 */
	CommonResult receiveDailyData(CryptoCoinDataDTO dto, Boolean updateOthers);

	CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinCatalog coinType, CurrencyType currencyType,
			LocalDateTime datetime);

	void findMissingDailyData(Long preCoinType);

}
