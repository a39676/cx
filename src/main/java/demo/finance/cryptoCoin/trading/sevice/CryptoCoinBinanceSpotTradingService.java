package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotQueryOrdersDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

public interface CryptoCoinBinanceSpotTradingService {

	ModelAndView tradingView();

	ModelAndView getPositionInfo(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getOpenOrders(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getOrdersBySymbol(CryptoCoinBinanceSpotQueryOrdersDTO dto);

	ModelAndView getWalletBalance(CryptoCoinInteractionCommonDTO dto);

}
