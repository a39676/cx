package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinSpotSetOrderForMultipleUserDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotQueryOrdersDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotSetOrderDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;

public interface CryptoCoinBinanceSpotTradingService {

	ModelAndView tradingView();

	ModelAndView getPositionInfo(CryptoCoinInteractionSingleUserCommonDTO dto);

	ModelAndView getOpenOrders(CryptoCoinInteractionSingleUserCommonDTO dto);

	ModelAndView getOrdersBySymbol(CryptoCoinBinanceSpotQueryOrdersDTO dto);

	ModelAndView getWalletBalance(CryptoCoinInteractionSingleUserCommonDTO dto);

	CommonResult sendOrder(CryptoCoinSpotSetOrderDTO dto);

	CommonResult sendOrderForMultipleUser(CryptoCoinSpotSetOrderForMultipleUserDTO dto);

}
