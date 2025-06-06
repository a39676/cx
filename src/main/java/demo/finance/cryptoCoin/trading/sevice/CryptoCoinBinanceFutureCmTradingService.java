package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmSetOrderCxDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinCloseLongShortPositionByMarketOrderDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinCloseLongShortPositionByMarketOrderForMultipleUsersDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderByIdDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotQueryOrdersDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;

public interface CryptoCoinBinanceFutureCmTradingService {

	ModelAndView getFutureCmPositionInfo(CryptoCoinInteractionSingleUserCommonDTO dto);

	ModelAndView getFutureCmOpenOrders(CryptoCoinInteractionSingleUserCommonDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureCmSetOrderCxDTO dto);

	CommonResult cancleMultipleOrder(CryptoCoinBinanceFutureCmCancelMultipleOrderDTO dto);

	CommonResult cancleOrderById(CryptoCoinBinanceFutureCmCancelOrderByIdDTO dto);
	
	CommonResult cancleMultipleOrderForMultipleUser(CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO dto);

	CommonResult sendFutureOrderForMultipleUser(CryptoCoinBinanceFutureCmSetOrderForMultipleUserDTO dto);

	CommonResult closeBothLongShortPositionByMarket(CryptoCoinCloseLongShortPositionByMarketOrderDTO dto);

	CommonResult closeBothLongShortPositionByMarketForMultipleUser(
			CryptoCoinCloseLongShortPositionByMarketOrderForMultipleUsersDTO dto);

	ModelAndView getOrdersBySymbol(CryptoCoinBinanceSpotQueryOrdersDTO dto);

}
