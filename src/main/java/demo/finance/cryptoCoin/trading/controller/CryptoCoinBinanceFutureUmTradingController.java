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
import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinBinanceTradingUrl;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmGetOrderBySymbolDTO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmTradingService;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;

@Controller
@RequestMapping(value = CryptoCoinBinanceTradingUrl.FUTURE_UM_ROOT)
public class CryptoCoinBinanceFutureUmTradingController {

	@Autowired
	private CryptoCoinBinanceFutureUmTradingService binanceFutureUmTradingService;

	@GetMapping(value = CryptoCoinBinanceTradingUrl.FUTURE_UM_VIEW)
	public ModelAndView tradingView() {
		return binanceFutureUmTradingService.tradingView();
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_SEND_ORDER)
	@ResponseBody
	public CommonResult sendFutureOrder(@RequestBody CryptoCoinBinanceFutureUmBatchOrderDTO dto) {
		return binanceFutureUmTradingService.sendFutureOrder(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_BTC_ARBITRAGE_BATCH_ORDER)
	@ResponseBody
	public CommonResult sendBtcArbitrageWithBatchOrder(
			@RequestBody CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto) {
		return binanceFutureUmTradingService.sendBtcArbitrageWithBatchOrder(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_BATCH_ORDER_MODIFY)
	@ResponseBody
	public CommonResult batchOrderModify(@RequestBody BinanceUpdateOrderDTO dto) {
		return binanceFutureUmTradingService.batchOrderModify(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_CLOSE_POSITION_BY_RATIO)
	@ResponseBody
	public CommonResult closePositionByRatio(@RequestBody CryptoCoinBinanceFutureUmBatchOrderDTO dto) {
		return binanceFutureUmTradingService.closePositionByRatio(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.ADD_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CommonResult addShortingSymbolList(@RequestBody CryptoCoinAddSymbolGroupDTO dto) {
		return binanceFutureUmTradingService.addSymbolGroup(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.DEL_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CommonResult deleteShortingSymbolList(@RequestBody BaseStrDTO dto) {
		return binanceFutureUmTradingService.deleteSymbolGroup(dto);
	}

	@PostMapping(value = CcmUrlConstant.POSITION_INFO_UM)
	@ResponseBody
	public ModelAndView getPositionInfoUm(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceFutureUmTradingService.getFutureUmPositionInfo(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_OPEN_ORDERS_UM)
	@ResponseBody
	public ModelAndView getOpenOrdersUm(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceFutureUmTradingService.getFutureUmOpenOrders(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_ORDERS_BY_SYMBOL_UM)
	@ResponseBody
	public ModelAndView getOpenOrdersUm(@RequestBody CryptoCoinBinanceFutureUmGetOrderBySymbolDTO dto) {
		return binanceFutureUmTradingService.getOrdersBySymbol(dto);
	}

}
