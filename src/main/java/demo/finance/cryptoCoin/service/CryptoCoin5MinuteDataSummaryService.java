package demo.finance.cryptoCoin.service;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinHistoryPriceDTO;

public interface CryptoCoin5MinuteDataSummaryService {

	CommonResult reciveCoinHistoryPrice(CryptoCoinHistoryPriceDTO dto);

}
