package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.po.dto.AddSymbolGroupDTO;
import finance.cryptoCoin.binance.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureBatchOrderDTO;

public interface CryptoCoinBinanceFutureTradingService {

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceBtArbitrageWithBatchDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureBatchOrderDTO dto);

	CommonResult batchOrderModify(BinanceUpdateOrderDTO dto);

	CommonResult closePositionByRatio(CryptoCoinBinanceFutureBatchOrderDTO dto);

	CommonResult addSymbolGroup(AddSymbolGroupDTO dto);

	CommonResult deleteSymbolGroup(BaseStrDTO dto);

}
