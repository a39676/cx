package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmGetOrderBySymbolDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;

public interface CryptoCoinBinanceFutureUmTradingService {

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureUmBatchOrderDTO dto);

	CommonResult batchOrderModify(BinanceUpdateOrderDTO dto);

	CommonResult closePositionByRatio(CryptoCoinBinanceFutureUmBatchOrderDTO dto);

	CommonResult addSymbolGroup(CryptoCoinAddSymbolGroupDTO dto);

	CommonResult deleteSymbolGroup(BaseStrDTO dto);

	CryptoCoinSymbolGroupSettingDTO getSymbolGroupData();

	ModelAndView getFutureUmPositionInfo(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getFutureUmOpenOrders(CryptoCoinInteractionCommonDTO dto);

	ModelAndView getOrdersBySymbol(CryptoCoinBinanceFutureUmGetOrderBySymbolDTO dto);

}
