package demo.finance.cryptoCoin.data.service;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.data.pojo.dto.GetBigMoveSummaryDataDTO;
import demo.finance.cryptoCoin.data.pojo.result.GetBigMoveSummaryDataResult;
import finance.cryptoCoin.common.pojo.result.CryptoCoinSymbolMaxLeverageResult;

public interface CryptoCoinDataComplexService {

	void receiveNewBigMoveSpotDataMessage(String msg);

	ModelAndView getBigMoveSpotSummaryView();

	GetBigMoveSummaryDataResult getBigMoveSummaryDataTable(GetBigMoveSummaryDataDTO dto);

	void sendBigMoveDataCrossResult();

	ModelAndView getBigMoveDataChartBySymbol(GetBigMoveSummaryDataDTO dto);

	ModelAndView getBigMoveDataChart(GetBigMoveSummaryDataDTO dto);

	void loadSymbolMaxLeverageInfoToCache();

	void receiveSymbolMaxLeverageInfo(CryptoCoinSymbolMaxLeverageResult result);

}
