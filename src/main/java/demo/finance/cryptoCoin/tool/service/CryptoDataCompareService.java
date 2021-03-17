package demo.finance.cryptoCoin.tool.service;

import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareResult;

public interface CryptoDataCompareService {

	/**
	 * TODO
	 * 2021-03-16
	 * 正在完善
	 * @param dto
	 * @return
	 */
	CryptoDataCompareResult cryptoCoinDataCompareLine(CryptoCoinDataCompareDTO dto);

	CryptoDataCompareResult cryptoCoinDailyDataComparePoint(CryptoCoinDataCompareDTO dto);

}
