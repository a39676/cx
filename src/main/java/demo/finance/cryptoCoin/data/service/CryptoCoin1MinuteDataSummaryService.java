package demo.finance.cryptoCoin.data.service;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1minute;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public interface CryptoCoin1MinuteDataSummaryService {

	CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto);

	CommonResult deleteExpiredCacheData();

	List<CryptoCoinPrice1minute> getData(CryptoCoinType coinType, CurrencyType currencyType, Integer minutes);

}
