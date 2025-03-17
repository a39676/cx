package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2CxDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderByIdDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmCancelMultipleOrderDTO;

public interface CryptoCoinBinanceFutureUmTradingService {

	ModelAndView tradingViewV2();

	CommonResult sendOrder(CryptoCoinBinanceFutureUmSetOrderV2CxDTO dto);

	CommonResult sendFutureOrderForMultipleUser(CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO dto);

	CommonResult cancleMultipleOrder(CryptoCoinBinanceFutureUmCancelMultipleOrderDTO dto);

	CommonResult cancleMultipleOrderForMultipleUser(CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO dto);

	CommonResult cancleOrderById(CryptoCoinBinanceFutureCmCancelOrderByIdDTO dto);

}
