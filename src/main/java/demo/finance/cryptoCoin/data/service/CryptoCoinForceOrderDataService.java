package demo.finance.cryptoCoin.data.service;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBigTradeQueryDTO;

public interface CryptoCoinForceOrderDataService {

	void checkMostRecentForceOrderSummary();

	ModelAndView getBigForceOrderDataChartBySymbol(CryptoCoinBigTradeQueryDTO dto);

	void receiveNewForceOrderFutureUmDataMessage(String msg);

	ModelAndView getBigForceOrderDataChartBySymbol();

}
