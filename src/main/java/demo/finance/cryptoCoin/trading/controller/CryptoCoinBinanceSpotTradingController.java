package demo.finance.cryptoCoin.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinBinanceTradingUrl;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceSpotTradingService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotQueryOrdersDTO;
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

//	@PostMapping(value = CcmUrlConstant.GET_ORDERS_BY_SYMBOL_SPOT)
//	@ResponseBody
//	public ModelAndView getOpenOrdersUm(@RequestBody CryptoCoinBinanceQueryOrdersDTO dto) {
//		return binanceSpotTradingService.getOrdersBySymbol(dto);
//	}
	
	@PostMapping(value = CcmUrlConstant.GET_WALLET_BALANCE)
	@ResponseBody
	public ModelAndView getWalletBalance(@RequestBody CryptoCoinInteractionCommonDTO dto) {
		return binanceSpotTradingService.getWalletBalance(dto);
	}

	@GetMapping(value = CcmUrlConstant.GET_ORDERS_BY_SYMBOL_SPOT)
	@ResponseBody
	public ModelAndView getOpenOrdersUm(@RequestParam("symbol") String symbol, @RequestParam("userId") Integer userId,
			@RequestParam("nickname") String nickname) {
		CryptoCoinBinanceSpotQueryOrdersDTO dto = new CryptoCoinBinanceSpotQueryOrdersDTO();
		dto.setSymbol(symbol);
		dto.setUserId(userId);
		dto.setUserNickname(nickname);
		return binanceSpotTradingService.getOrdersBySymbol(dto);
	}
}
