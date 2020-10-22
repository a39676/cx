package demo.finance.cryptoCoin.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.pojo.result.CommonResultCX;
import finance.cryptoCoin.pojo.dto.CryptoCoinNewPriceDTO;

public interface CryptoCoinPriceService {

	CommonResult reciveCoinNewPrice(CryptoCoinNewPriceDTO dto);

	CommonResultCX deleteExpiredCacheData();

}
