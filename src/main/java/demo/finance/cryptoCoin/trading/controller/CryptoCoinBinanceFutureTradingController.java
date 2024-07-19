package demo.finance.cryptoCoin.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.po.constant.CryptoCoinBinanceTradingUrl;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureTradingService;
import finance.cryptoCoin.binance.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureBatchOrderDTO;

@Controller
@RequestMapping(value = CryptoCoinBinanceTradingUrl.ROOT)
public class CryptoCoinBinanceFutureTradingController {

	@Autowired
	private CryptoCoinBinanceFutureTradingService binanceFutureTradingService;

	@GetMapping(value = CryptoCoinBinanceTradingUrl.VIEW)
	public ModelAndView tradingView() {
		return binanceFutureTradingService.tradingView();
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_SEND_ORDER)
	@ResponseBody
	public CommonResult sendFutureOrder(@RequestBody CryptoCoinBinanceFutureBatchOrderDTO dto) {
		return binanceFutureTradingService.sendFutureOrder(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_BTC_ARBITRAGE_BATCH_ORDER)
	@ResponseBody
	public CommonResult sendBtcArbitrageWithBatchOrder(@RequestBody CryptoCoinBinanceBtArbitrageWithBatchDTO dto) {
		return binanceFutureTradingService.sendBtcArbitrageWithBatchOrder(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_BATCH_ORDER_MODIFY)
	@ResponseBody
	public CommonResult batchOrderModify(@RequestBody BinanceUpdateOrderDTO dto) {
		return binanceFutureTradingService.batchOrderModify(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_CLOSE_POSITION_BY_RATIO)
	@ResponseBody
	public CommonResult closePositionByRatio(@RequestBody CryptoCoinBinanceFutureBatchOrderDTO dto) {
		return binanceFutureTradingService.closePositionByRatio(dto);
	}
	
	@PostMapping(value = CryptoCoinBinanceTradingUrl.ADD_SHORTING_SYMBOL_DATA)
	@ResponseBody
	public CommonResult addShortingSymbolList(@RequestBody BaseStrDTO dto) {
		return binanceFutureTradingService.addShortingSymbolList(dto);
	}
	
	@PostMapping(value = CryptoCoinBinanceTradingUrl.DEL_SHORTING_SYMBOL_DATA)
	@ResponseBody
	public CommonResult deleteShortingSymbolList(@RequestBody BaseStrDTO dto) {
		return binanceFutureTradingService.deleteShortingSymbolList(dto);
	}
}
