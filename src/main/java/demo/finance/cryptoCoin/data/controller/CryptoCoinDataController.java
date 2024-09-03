package demo.finance.cryptoCoin.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataUrl;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBigTradeQueryDTO;
import demo.finance.cryptoCoin.data.pojo.dto.GetBigMoveSummaryDataDTO;
import demo.finance.cryptoCoin.data.pojo.result.GetBigMoveSummaryDataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;

@Controller
@RequestMapping(value = CryptoCoinDataUrl.ROOT)
public class CryptoCoinDataController extends CommonController {

	@Autowired
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;

	@GetMapping(value = CryptoCoinDataUrl.BIG_MOVE_SPOT_VIEW)
	public ModelAndView getBigMoveSummaryView() {
		return cryptoCoinDataComplexService.getBigMoveSpotSummaryView();
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_MOVE_SPOT_DATA_TABLE)
	@ResponseBody
	public GetBigMoveSummaryDataResult getBigMoveSummaryData(@RequestBody GetBigMoveSummaryDataDTO dto) {
		return cryptoCoinDataComplexService.getBigMoveSummaryDataTable(dto);
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_MOVE_CHART)
	@ResponseBody
	public ModelAndView getBigMoveSummaryChart(@RequestBody GetBigMoveSummaryDataDTO dto) {
		return cryptoCoinDataComplexService.getBigMoveDataChart(dto);
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_MOVE_CHART_BY_SYMBOL)
	@ResponseBody
	public ModelAndView getBigMoveSummaryChartBySymbol(@RequestBody GetBigMoveSummaryDataDTO dto) {
		return cryptoCoinDataComplexService.getBigMoveDataChartBySymbol(dto);
	}

	@GetMapping(value = CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_CHART_BY_SYMBOL)
	public ModelAndView getBigMoveSummaryChartBySymbol(@RequestParam(name = "symbol", required = false) String symbol) {
		return cryptoCoinDataComplexService.getBigTradeDataBubbleChartBySymbol(symbol);
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_BUBBLE_CHART_BY_SYMBOL)
	public ModelAndView getBigTradeBubbleChartBySymbol(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinDataComplexService.getBigTradeBubbleChartBySymbol(dto);
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_LINE_CHART_BY_SYMBOL)
	public ModelAndView getBigTradeLineChartBySymbol(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinDataComplexService.getBigTradeLineChartBySymbol(dto);
	}

	@GetMapping(value = CryptoCoinDataUrl.BIG_FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL)
	public ModelAndView getBigForceOrderDataChartBySymbol() {
		return cryptoCoinDataComplexService.getBigForceOrderDataChartBySymbol();
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL)
	public ModelAndView getBigForceOrderDataChartBySymbol(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinDataComplexService.getBigForceOrderDataChartBySymbol(dto);
	}

}
