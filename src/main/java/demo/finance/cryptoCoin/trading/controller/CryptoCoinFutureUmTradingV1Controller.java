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
import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinTradingUrl;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmGetOrderBySymbolDTO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmSingleUserGroupOrderTradingService;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderV1DTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;

@Controller
@RequestMapping(value = CryptoCoinTradingUrl.FUTURE_UM_ROOT)
public class CryptoCoinFutureUmTradingV1Controller {

	@Autowired
	private CryptoCoinBinanceFutureUmSingleUserGroupOrderTradingService binanceFutureUmSingleUserGroupOrderTradingService;

	@GetMapping(value = CryptoCoinTradingUrl.FUTURE_UM_VIEW_V1)
	public ModelAndView tradingView() {
		return binanceFutureUmSingleUserGroupOrderTradingService.tradingViewV1();
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_SEND_ORDER_V1)
	@ResponseBody
	public CommonResult sendFutureOrder(@RequestBody CryptoCoinBinanceFutureUmBatchOrderV1DTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.sendFutureOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_BTC_ARBITRAGE_BATCH_ORDER)
	@ResponseBody
	public CommonResult sendBtcArbitrageWithBatchOrder(
			@RequestBody CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.sendBtcArbitrageWithBatchOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_BATCH_ORDER_MODIFY)
	@ResponseBody
	public CommonResult batchOrderModify(@RequestBody BinanceUpdateOrderDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.batchOrderModify(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_CLOSE_POSITION_BY_RATIO)
	@ResponseBody
	public CommonResult closePositionByRatio(@RequestBody CryptoCoinBinanceFutureUmBatchOrderV1DTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.closePositionByRatio(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.ADD_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CommonResult addShortingSymbolList(@RequestBody CryptoCoinAddSymbolGroupDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.addSymbolGroup(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.DEL_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CommonResult deleteShortingSymbolList(@RequestBody BaseStrDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.deleteSymbolGroup(dto);
	}

	@PostMapping(value = CcmUrlConstant.POSITION_INFO_UM)
	@ResponseBody
	public ModelAndView getPositionInfoUm(@RequestBody CryptoCoinInteractionSingleUserCommonDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.getFutureUmPositionInfo(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_OPEN_ORDERS_UM)
	@ResponseBody
	public ModelAndView getOpenOrdersUm(@RequestBody CryptoCoinInteractionSingleUserCommonDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.getFutureUmOpenOrders(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_ORDERS_BY_SYMBOL_UM)
	@ResponseBody
	public ModelAndView getOrdersBySymbol(@RequestBody CryptoCoinBinanceFutureUmGetOrderBySymbolDTO dto) {
		return binanceFutureUmSingleUserGroupOrderTradingService.getOrdersBySymbol(dto);
	}

}
