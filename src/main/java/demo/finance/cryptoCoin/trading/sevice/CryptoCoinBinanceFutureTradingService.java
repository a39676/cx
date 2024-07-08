package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureBatchOrderDTO;

public interface CryptoCoinBinanceFutureTradingService {

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceBtArbitrageWithBatchDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureBatchOrderDTO dto);

}
