package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

public interface CryptoCoinBinanceFutureCmTradingService {

	ModelAndView getFutureCmPositionInfo(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getFutureCmOpenOrders(CryptoCoinInteractionCommonDTO dto);

	ModelAndView tradingView();

}
