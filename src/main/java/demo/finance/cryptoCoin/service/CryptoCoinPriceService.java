package demo.finance.cryptoCoin.service;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinPriceDTO;

public interface CryptoCoinPriceService {

	CommonResult reciveCoinPrice(CryptoCoinPriceDTO dto);

}
