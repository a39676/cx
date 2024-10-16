package demo.finance.cryptoCoin.trading.sevice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceFutureCmOpenOrderResponseSubVO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureCmTradingService;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceCmFuturePositionInfoResult;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinBinanceFutureCmTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceFutureCmTradingService {

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureCmOrder");
		v.addObject("title", "BinanceTrading(Future CM)");
		v.addObject("userList", optionService.getUserMetaData());
		return v;
	}

	@Override
	public ModelAndView getFutureCmPositionInfo(CryptoCoinInteractionCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getFutureCmPositionTable");
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.POSITION_INFO_CM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		CryptoCoinBinanceCmFuturePositionInfoResult r;
		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceCmFuturePositionInfoResult.class);
			if (r.isFail()) {
				v.addObject("msg", r.getMessage());
				return v;
			}
			v.addObject("dataList", r.getPositionList());
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public ModelAndView getFutureCmOpenOrders(CryptoCoinInteractionCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getFutureCmOpenOrdersTable");
		List<CryptoCoinBinanceFutureCmOpenOrderResponseSubVO> list = new ArrayList<>();
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_OPEN_ORDERS_CM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		try {
			String response = h.sendPostRestful(url, json.toString());
			JSONArray jsonArray = JSONArray.fromObject(response);
			CryptoCoinBinanceFutureCmOpenOrderResponseSubVO dataVO = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				try {
					dataVO = buildObjFromJsonCustomization(jsonArray.getString(i),
							CryptoCoinBinanceFutureCmOpenOrderResponseSubVO.class);
					dataVO.setOrderTimeStr(localDateTimeHandler
							.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(dataVO.getTime()))));
					dataVO.setUpdateTimeStr(localDateTimeHandler
							.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(dataVO.getUpdateTime()))));
					if (BinanceOrderSideType.BUY.equals(dataVO.getSide())
							&& BinancePositionSideType.LONG.equals(dataVO.getPositionSide())) {
						dataVO.setOrderTypeInSimpleWord("开多");
					} else if (BinanceOrderSideType.BUY.equals(dataVO.getSide())
							&& BinancePositionSideType.SHORT.equals(dataVO.getPositionSide())) {
						dataVO.setOrderTypeInSimpleWord("平空");
					} else if (BinanceOrderSideType.SELL.equals(dataVO.getSide())
							&& BinancePositionSideType.LONG.equals(dataVO.getPositionSide())) {
						dataVO.setOrderTypeInSimpleWord("平多");
					} else if (BinanceOrderSideType.SELL.equals(dataVO.getSide())
							&& BinancePositionSideType.SHORT.equals(dataVO.getPositionSide())) {
						dataVO.setOrderTypeInSimpleWord("开空");
					} else {
						dataVO.setOrderTypeInSimpleWord(
								dataVO.getSide().getName() + "," + dataVO.getPositionSide().getName());
					}
					list.add(dataVO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		v.addObject("dataList", list);
		return v;
	}

}
