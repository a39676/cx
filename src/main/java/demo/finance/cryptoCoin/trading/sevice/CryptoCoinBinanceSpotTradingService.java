package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

public interface CryptoCoinBinanceSpotTradingService {

	ModelAndView tradingView();

	ModelAndView getPositionInfo(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getOpenOrders(CryptoCoinInteractionCommonDTO dto);

}
