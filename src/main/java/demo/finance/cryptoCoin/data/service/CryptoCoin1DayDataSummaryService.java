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

	List<CryptoCoinPrice1day> getDataList(CryptoCoinType coinType, CurrencyType currencyType, LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataList(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	List<CryptoCoinPriceCommonDataBO> getCommonDataListFillWithCache(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime startTime);

	CommonResult reciveDailyData(CryptoCoinDataDTO dto);

	CryptoCoinPriceCommonDataBO getCommonData(CryptoCoinType coinType, CurrencyType currencyType,
			LocalDateTime datetime);

}
