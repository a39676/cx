package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.binance.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceBtArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.pojo.dto.CryptoCoinBinanceFutureBatchOrderDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;

public interface CryptoCoinBinanceFutureTradingService {

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceBtArbitrageWithBatchDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureBatchOrderDTO dto);

	CommonResult batchOrderModify(BinanceUpdateOrderDTO dto);

	CommonResult closePositionByRatio(CryptoCoinBinanceFutureBatchOrderDTO dto);

	CommonResult addSymbolGroup(CryptoCoinAddSymbolGroupDTO dto);

	CommonResult deleteSymbolGroup(BaseStrDTO dto);

	CryptoCoinSymbolGroupSettingDTO getSymbolGroupData();

}
