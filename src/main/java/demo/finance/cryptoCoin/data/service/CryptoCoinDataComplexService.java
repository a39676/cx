package demo.finance.cryptoCoin.data.service;

import org.springframework.web.servlet.ModelAndView;

public interface CryptoCoinDataComplexService {

	void getNewBigMoveDataMessage(String msg);

	ModelAndView getBigMoveSummaryByManual();

}
