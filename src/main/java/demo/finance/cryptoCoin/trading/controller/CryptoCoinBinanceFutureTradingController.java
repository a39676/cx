package demo.finance.cryptoCoin.trading.controller;

import java.util.List;

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
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureTradingService;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmOpenOrderResponseSubDTO;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceUmFuturePositionInfoResult;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinOrderCommonDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;

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
	public CommonResult sendFutureOrder(@RequestBody CryptoCoinBinanceFutureUmBatchOrderDTO dto) {
		return binanceFutureTradingService.sendFutureOrder(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_BTC_ARBITRAGE_BATCH_ORDER)
	@ResponseBody
	public CommonResult sendBtcArbitrageWithBatchOrder(
			@RequestBody CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto) {
		return binanceFutureTradingService.sendBtcArbitrageWithBatchOrder(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_BATCH_ORDER_MODIFY)
	@ResponseBody
	public CommonResult batchOrderModify(@RequestBody BinanceUpdateOrderDTO dto) {
		return binanceFutureTradingService.batchOrderModify(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.BINANCE_FUTURE_UM_CLOSE_POSITION_BY_RATIO)
	@ResponseBody
	public CommonResult closePositionByRatio(@RequestBody CryptoCoinBinanceFutureUmBatchOrderDTO dto) {
		return binanceFutureTradingService.closePositionByRatio(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.ADD_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CommonResult addShortingSymbolList(@RequestBody CryptoCoinAddSymbolGroupDTO dto) {
		return binanceFutureTradingService.addSymbolGroup(dto);
	}

	@PostMapping(value = CryptoCoinBinanceTradingUrl.DEL_SYMBOL_GROUP_DATA)
	@ResponseBody
	public CommonResult deleteShortingSymbolList(@RequestBody BaseStrDTO dto) {
		return binanceFutureTradingService.deleteSymbolGroup(dto);
	}
	
	@PostMapping(value = CcmUrlConstant.POSITION_INFO)
	public CryptoCoinBinanceUmFuturePositionInfoResult getPositionInfo(@RequestBody CryptoCoinOrderCommonDTO dto) {
		return binanceFutureTradingService.getPositionInfo(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_OPEN_ORDERS)
	public List<CryptoCoinBinanceFutureUmOpenOrderResponseSubDTO> getOpenOrders(
			@RequestBody CryptoCoinOrderCommonDTO dto) {
		return binanceFutureTradingService.getOpenOrders(dto);
	}
}
