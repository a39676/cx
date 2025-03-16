package demo.finance.cryptoCoin.trading.sevice;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmGetOrderBySymbolDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderV1DTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;

public interface CryptoCoinBinanceFutureUmSingleUserGroupOrderTradingService {

	CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto);

	ModelAndView tradingViewV1();

	CommonResult sendFutureOrder(CryptoCoinBinanceFutureUmBatchOrderV1DTO dto);

	CommonResult batchOrderModify(BinanceUpdateOrderDTO dto);

	CommonResult closePositionByRatio(CryptoCoinBinanceFutureUmBatchOrderV1DTO dto);

	CommonResult addSymbolGroup(CryptoCoinAddSymbolGroupDTO dto);

	CommonResult deleteSymbolGroup(BaseStrDTO dto);

	CryptoCoinSymbolGroupSettingDTO getSymbolGroupData();

	ModelAndView getFutureUmPositionInfo(CryptoCoinInteractionSingleUserCommonDTO dto);

	ModelAndView getFutureUmOpenOrders(CryptoCoinInteractionSingleUserCommonDTO dto);

	ModelAndView getOrdersBySymbol(CryptoCoinBinanceFutureUmGetOrderBySymbolDTO dto);

}
