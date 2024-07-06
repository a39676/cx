package demo.finance.cryptoCoin.trading.sevice;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureOrderDTO;

public interface CryptoCoinBinanceFutureTradingService {

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureOrderDTO dto);

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceBtArbitrageWithBatchDTO dto);

}
