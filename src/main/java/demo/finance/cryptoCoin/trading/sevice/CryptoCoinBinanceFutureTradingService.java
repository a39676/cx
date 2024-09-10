package demo.finance.cryptoCoin.trading.sevice;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmOpenOrderResponseSubDTO;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceUmFuturePositionInfoResult;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinOrderCommonDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;

public interface CryptoCoinBinanceFutureTradingService {

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto);

	ModelAndView tradingView();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureUmBatchOrderDTO dto);

	CommonResult batchOrderModify(BinanceUpdateOrderDTO dto);

	CommonResult closePositionByRatio(CryptoCoinBinanceFutureUmBatchOrderDTO dto);

	CommonResult addSymbolGroup(CryptoCoinAddSymbolGroupDTO dto);

	CommonResult deleteSymbolGroup(BaseStrDTO dto);

	CryptoCoinSymbolGroupSettingDTO getSymbolGroupData();

	CryptoCoinBinanceUmFuturePositionInfoResult getPositionInfo(CryptoCoinOrderCommonDTO dto);

	List<CryptoCoinBinanceFutureUmOpenOrderResponseSubDTO> getOpenOrders(CryptoCoinOrderCommonDTO dto);

}
