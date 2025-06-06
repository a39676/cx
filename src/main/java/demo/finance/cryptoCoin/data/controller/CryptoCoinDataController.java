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
import demo.finance.cryptoCoin.data.service.CryptoCoinBigTradeDataService;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;
import demo.finance.cryptoCoin.data.service.CryptoCoinForceOrderDataService;

@Controller
@RequestMapping(value = CryptoCoinDataUrl.ROOT)
public class CryptoCoinDataController extends CommonController {

	@Autowired
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;
	@Autowired
	private CryptoCoinBigTradeDataService cryptoCoinBigTradeDataService;
	@Autowired
	private CryptoCoinForceOrderDataService cryptoCoinForceOrderDataService;

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
	public ModelAndView getBigMoveSummaryChartBySymbol(@RequestParam(name = "symbol", required = false) String symbol,
			@RequestParam(name = "start", required = false, defaultValue = "0") Integer hourRangeStart,
			@RequestParam(name = "end", required = false, defaultValue = "0") Integer hourRangeEnd) {
		return cryptoCoinBigTradeDataService.getBigTradeDataBubbleChartBySymbol(symbol, hourRangeStart, hourRangeEnd);
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_BUBBLE_CHART_BY_SYMBOL)
	public ModelAndView getBigTradeBubbleChartBySymbol(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinBigTradeDataService.getBigTradeBubbleChartBySymbol(dto);
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_LINE_CHART_BY_SYMBOL)
	public ModelAndView getBigTradeLineChartBySymbol(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinBigTradeDataService.getBigTradeLineChartBySymbol(dto);
	}

	@GetMapping(value = CryptoCoinDataUrl.FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL)
	public ModelAndView getBigForceOrderDataChartBySymbol() {
		return cryptoCoinForceOrderDataService.getBigForceOrderDataChartBySymbol();
	}

	@PostMapping(value = CryptoCoinDataUrl.FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL)
	public ModelAndView getBigForceOrderDataChartBySymbol(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinForceOrderDataService.getBigForceOrderDataChartBySymbol(dto);
	}

	@GetMapping(value = CryptoCoinDataUrl.BIG_TRADE_DATA_TABLE)
	public ModelAndView getBigTradeSummaryDataTable() {
		return cryptoCoinBigTradeDataService.getBigTradeSummaryDataTable();
	}

	@PostMapping(value = CryptoCoinDataUrl.BIG_TRADE_DATA_TABLE)
	public ModelAndView getBigTradeSummaryDataTable(@RequestBody CryptoCoinBigTradeQueryDTO dto) {
		return cryptoCoinBigTradeDataService.getBigTradeSummaryDataTable(dto);
	}
}
