package demo.finance.cryptoCoin.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinBinanceTradingUrl;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureCmTradingService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

@Controller
@RequestMapping(value = CryptoCoinBinanceTradingUrl.FUTURE_CM_ROOT)
public class CryptoCoinBinanceFutureCmTradingController {

	@Autowired
	private CryptoCoinBinanceFutureCmTradingService binanceFutureCmTradingService;

	@GetMapping(value = CryptoCoinBinanceTradingUrl.FUTURE_CM_VIEW)
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
}
