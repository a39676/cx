package demo.finance.cryptoCoin.data.service;

import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.data.pojo.dto.GetBigMoveSummaryDataDTO;
import demo.finance.cryptoCoin.data.pojo.result.GetBigMoveSummaryDataResult;

public interface CryptoCoinDataComplexService {

	void receiveNewBigMoveDataMessage(String msg);

	ModelAndView getBigMoveSummaryView();

	GetBigMoveSummaryDataResult getBigMoveSummaryData(GetBigMoveSummaryDataDTO dto);

	void sendBigMoveDataCrossResult();

	ModelAndView getBigMoveDataChart(Integer days);

}
