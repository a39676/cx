package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmSetOrderDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;

public interface CryptoCoinBinanceFutureCmTradingService {

	ModelAndView getFutureCmPositionInfo(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getFutureCmOpenOrders(CryptoCoinInteractionCommonDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureCmSetOrderDTO dto);

	CommonResult cancleFutureOrder(CryptoCoinBinanceFutureCmCancelOrderDTO dto);

}
