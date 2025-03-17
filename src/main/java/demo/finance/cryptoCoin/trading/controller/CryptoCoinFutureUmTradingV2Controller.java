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
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2CxDTO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmTradingService;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderByIdDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmCancelMultipleOrderDTO;

@Controller
@RequestMapping(value = CryptoCoinTradingUrl.FUTURE_UM_ROOT)
public class CryptoCoinFutureUmTradingV2Controller {

	@Autowired
	private CryptoCoinBinanceFutureUmTradingService umTradingService;

	@GetMapping(value = CryptoCoinTradingUrl.FUTURE_UM_VIEW_V2)
	public ModelAndView tradingView() {
		return umTradingService.tradingViewV2();
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_SEND_ORDER_V2)
	@ResponseBody
	public CommonResult sendFutureOrder(@RequestBody CryptoCoinBinanceFutureUmSetOrderV2CxDTO dto) {
		return umTradingService.sendOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_SEND_ORDER_MULTIPLE_USER)
	@ResponseBody
	public CommonResult sendFutureOrderForMultipleUser(
			@RequestBody CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO dto) {
		return umTradingService.sendFutureOrderForMultipleUser(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_CANCEL_MULTIPLE_ORDER)
	@ResponseBody
	public CommonResult cancleMultipleOrder(@RequestBody CryptoCoinBinanceFutureUmCancelMultipleOrderDTO dto) {
		return umTradingService.cancleMultipleOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_CANCEL_MULTIPLE_ORDER_MULTIPLE_USER)
	@ResponseBody
	public CommonResult cancleMultipleOrderForMultipleUser(
			@RequestBody CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO dto) {
		return umTradingService.cancleMultipleOrderForMultipleUser(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_FUTURE_UM_CANCEL_ORDER_BY_ID)
	@ResponseBody
	public CommonResult cancleOrderById(@RequestBody CryptoCoinBinanceFutureCmCancelOrderByIdDTO dto) {
		return umTradingService.cancleOrderById(dto);
	}

}
