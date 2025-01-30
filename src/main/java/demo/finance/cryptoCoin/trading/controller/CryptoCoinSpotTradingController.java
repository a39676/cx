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

import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.controller.CommonController;
import demo.finance.cryptoCoin.trading.pojo.constant.CryptoCoinTradingUrl;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinSpotSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceSpotTradingService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotQueryOrdersDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotCancelMultipleOrderDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotCancelOrderByIdDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotSetOrderDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;

@Controller
@RequestMapping(value = CryptoCoinTradingUrl.SPOT_ROOT)
public class CryptoCoinSpotTradingController extends CommonController {

	@Autowired
	private CryptoCoinBinanceSpotTradingService binanceSpotTradingService;

	@GetMapping(value = CryptoCoinTradingUrl.SPOT_VIEW)
	public ModelAndView tradingView() {
		return binanceSpotTradingService.tradingView();
	}

	@PostMapping(value = CcmUrlConstant.POSITION_INFO_SPOT)
	@ResponseBody
	public ModelAndView getPositionInfoUm(@RequestBody CryptoCoinInteractionSingleUserCommonDTO dto) {
		return binanceSpotTradingService.getPositionInfo(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_OPEN_ORDERS_SPOT)
	@ResponseBody
	public ModelAndView getOpenOrdersUm(@RequestBody CryptoCoinInteractionSingleUserCommonDTO dto) {
		return binanceSpotTradingService.getOpenOrders(dto);
	}

	@PostMapping(value = CcmUrlConstant.GET_WALLET_BALANCE)
	@ResponseBody
	public ModelAndView getWalletBalance(@RequestBody CryptoCoinInteractionSingleUserCommonDTO dto) {
		return binanceSpotTradingService.getWalletBalance(dto);
	}

	@GetMapping(value = CcmUrlConstant.GET_ORDERS_BY_SYMBOL_SPOT)
	@ResponseBody
	public ModelAndView getOrderHistory(@RequestParam("symbol") String symbol, @RequestParam("userId") Integer userId,
			@RequestParam("nickname") String nickname) {
		CryptoCoinBinanceSpotQueryOrdersDTO dto = new CryptoCoinBinanceSpotQueryOrdersDTO();
		dto.setSymbol(symbol);
		dto.setUserId(userId);
		dto.setUserNickname(nickname);
		dto.setExchangeCode(CryptoExchangeType.BINANCE.getCode());
		return binanceSpotTradingService.getOrdersBySymbol(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.SPOT_SEND_ORDER)
	@ResponseBody
	public CommonResult sendOrder(@RequestBody CryptoCoinSpotSetOrderDTO dto) {
		return binanceSpotTradingService.sendOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.SPOT_SEND_ORDER_MULTIPLE)
	@ResponseBody
	public CommonResult sendOrderForMultipleUser(@RequestBody CryptoCoinSpotSetOrderForMultipleUserDTO dto) {
		return binanceSpotTradingService.sendOrderForMultipleUser(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.SPOT_CANCEL_MULTIPLE_ORDER)
	@ResponseBody
	public CommonResult cancleMultipleOrder(@RequestBody CryptoCoinSpotCancelMultipleOrderDTO dto) {
		return binanceSpotTradingService.cancleMultipleOrder(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.SPOT_CANCEL_MULTIPLE_ORDER_MULTIPLE_USER)
	@ResponseBody
	public CommonResult cancleMultipleOrderForMultipleUser(
			@RequestBody CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO dto) {
		return binanceSpotTradingService.cancleMultipleOrderForMultipleUser(dto);
	}

	@PostMapping(value = CryptoCoinTradingUrl.BINANCE_SPOT_CANCEL_ORDER_BY_ID)
	@ResponseBody
	public CommonResult cancleOrderById(@RequestBody CryptoCoinSpotCancelOrderByIdDTO dto) {
		return binanceSpotTradingService.cancleOrderById(dto);
	}
}
