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
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceSpotTradingService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

@Controller
@RequestMapping(value = CryptoCoinBinanceTradingUrl.SPOT_ROOT)
public class CryptoCoinBinanceSpotTradingController {

	@Autowired
	private CryptoCoinBinanceSpotTradingService binanceSpotTradingService;

	@GetMapping(value = CryptoCoinBinanceTradingUrl.SPOT_VIEW)
	public ModelAndView tradingView() {
		return binanceSpotTradingService.tradingView();
	}

	@PostMapping(value = CcmUrlConstant.POSITION_INFO_SPOT)
	@ResponseBody
	public ModelAndView getPositionInfoUm(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceSpotTradingService.getPositionInfo(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_OPEN_ORDERS_SPOT)
	@ResponseBody
	public ModelAndView getOpenOrdersUm(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceSpotTradingService.getOpenOrders(dto);
	}
}
