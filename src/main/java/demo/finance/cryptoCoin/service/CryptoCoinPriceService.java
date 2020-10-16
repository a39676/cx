package demo.finance.cryptoCoin.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import finance.cryptoCoin.pojo.dto.CryptoCoinPriceDTO;

public interface CryptoCoinPriceService {

	CommonResult reciveCoinPrice(CryptoCoinPriceDTO dto);

	CommonResultCX deleteExpiredCacheData();

}
