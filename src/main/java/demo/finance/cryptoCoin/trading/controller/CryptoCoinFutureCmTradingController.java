package demo.finance.cryptoCoin.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinTradingUrl;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureCmTradingService;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderByIdDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmSetOrderDTO;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

@Controller
@RequestMapping(value = CryptoCoinTradingUrl.FUTURE_CM_ROOT)
public class CryptoCoinFutureCmTradingController {

	@Autowired
	private CryptoCoinBinanceFutureCmTradingService binanceFutureCmTradingService;

	@GetMapping(value = CryptoCoinTradingUrl.FUTURE_CM_VIEW)
	public ModelAndView tradingView() {
		return binanceFutureCmTradingService.tradingView();
	}

	@PostMapping(value = CcmUrlConstant.POSITION_INFO_CM)
	@ResponseBody
	public ModelAndView getPositionInfoCm(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceFutureCmTradingService.getFutureCmPositionInfo(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_OPEN_ORDERS_CM)
	@ResponseBody
	public ModelAndView getOpenOrdersCm(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceFutureCmTradingService.getFutureCmOpenOrders(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_CM_SEND_ORDER)
	@ResponseBody
	public CommonResult getOpenOrdersCm(@RequestBody CryptoCoinBinanceFutureCmSetOrderDTO dto) {
		return binanceFutureCmTradingService.sendFutureOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_CM_CANCEL_MULTIPLE_ORDER)
	@ResponseBody
	public CommonResult cancleMultipleOrder(@RequestBody CryptoCoinBinanceFutureCmCancelMultipleOrderDTO dto) {
		return binanceFutureCmTradingService.cancleMultipleOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_CM_CANCEL_ORDER_BY_ID)
	@ResponseBody
	public CommonResult cancleOrderById(@RequestBody CryptoCoinBinanceFutureCmCancelOrderByIdDTO dto) {
		return binanceFutureCmTradingService.cancleOrderById(dto);
	}
}
