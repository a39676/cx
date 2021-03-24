package demo.finance.cryptoCoin.tool.service;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataMutipleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinDataSingleCompareDTO;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareLineResult;
import demo.finance.cryptoCoin.tool.pojo.result.CryptoDataCompareRateResult;

public interface CryptoDataCompareService {

	/**
	 * TODO
	 * 2021-03-16
	 * 正在完善
	 * @param dto
	 * @return
	 */
	CryptoDataCompareLineResult cryptoCoinDataSingleCompareLine(CryptoCoinDataSingleCompareDTO dto);

	CryptoDataCompareRateResult cryptoCoinDailyDataSingleComparePoint(CryptoCoinDataSingleCompareDTO dto);

	CryptoDataCompareRateResult cryptoCoinDailyDataMutipleComparePoint(CryptoCoinDataMutipleCompareDTO dto);

	ModelAndView CryptoCoinDailyDataComparetor();

}
