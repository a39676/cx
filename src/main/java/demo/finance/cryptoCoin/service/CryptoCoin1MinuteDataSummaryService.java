package demo.finance.cryptoCoin.service;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;

public interface CryptoCoin1MinuteDataSummaryService {

	CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto);

	CommonResult deleteExpiredCacheData();

}
