package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2CxDTO;

public interface CryptoCoinBinanceFutureUmTradingService {

	ModelAndView tradingViewV2();

	CommonResult sendOrder(CryptoCoinBinanceFutureUmSetOrderV2CxDTO dto);

	CommonResult sendFutureOrderForMultipleUser(CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO dto);

}
