package demo.finance.cryptoCoin.data.service;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBigTradeQueryDTO;

public interface CryptoCoinBigTradeDataService {

	void receiveNewBigTradeFutureUmDataMessage(String msg);

	ModelAndView getBigTradeBubbleChartBySymbol(CryptoCoinBigTradeQueryDTO dto);

	ModelAndView getBigTradeLineChartBySymbol(CryptoCoinBigTradeQueryDTO dto);

	ModelAndView getBigTradeDataBubbleChartBySymbol(String symbol);

	ModelAndView getBigTradeSummaryDataTable();

	ModelAndView getBigTradeSummaryDataTable(CryptoCoinBigTradeQueryDTO dto);

}
