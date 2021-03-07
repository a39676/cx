package demo.finance.cryptoCoin.tool.service;

import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareResult;

public interface CryptoDataCompareService {

	CryptoDataCompareResult cryptoCoinDataCompare(CryptoCoinDataCompareDTO dto);

}
