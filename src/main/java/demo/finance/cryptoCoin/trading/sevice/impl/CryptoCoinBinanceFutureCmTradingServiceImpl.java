package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceCmFutureOrderProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceFutureCmCancelOrderProducer;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceFutureCmOpenOrderResponseSubVO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureCmTradingService;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmSetOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.result.CryptoCoinBinanceFutureCmQueryOrderResult;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceCmFuturePositionInfoResult;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceTimeInForceType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinBinanceFutureCmTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceFutureCmTradingService {

	@Autowired
	private CryptoCoinBinanceCmFutureOrderProducer cmFutureOrderProducer;
	@Autowired
	private CryptoCoinBinanceFutureCmCancelOrderProducer binanceFutureCmCancelOrderProducer;

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureCmOrder");
		v.addObject("title", "BinanceTrading(Future CM)");
		v.addObject("userList", optionService.getUserMetaData());
		v.addObject("exchangeList", CryptoExchangeType.values());
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
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_OPEN_ORDERS_CM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);
		List<CryptoCoinBinanceFutureCmOpenOrderResponseSubVO> list = new ArrayList<>();

		try {
			String response = h.sendPostRestful(url, json.toString());
			CryptoCoinBinanceFutureCmQueryOrderResult r = buildObjFromJsonCustomization(response,
					CryptoCoinBinanceFutureCmQueryOrderResult.class);
			List<CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO> orderList = r.getOrderList();
			CryptoCoinBinanceFutureCmOpenOrderResponseSubVO dataVO = null;
			for (int i = 0; i < orderList.size(); i++) {
				CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO order = orderList.get(i);
				try {
					dataVO = new CryptoCoinBinanceFutureCmOpenOrderResponseSubVO();
					BeanUtils.copyProperties(order, dataVO);
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
			v.addObject("msg", e.getLocalizedMessage());
		}
		v.addObject("dataList", list);
		return v;
	}

	@Override
	public CommonResult sendFutureOrder(CryptoCoinBinanceFutureCmSetOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		if (StringUtils.isBlank(dto.getSymbol())) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getQuantity() == null || dto.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
			r.failWithMessage("Quantity invalid");
			return r;
		}

		BinanceOrderSideType sideType = BinanceOrderSideType.getType(dto.getOrderSideCode());
		if (sideType == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}

		BinancePositionSideType positionSide = BinancePositionSideType.getType(dto.getPositionSideCode());
		if (positionSide == null) {
			r.failWithMessage("Position side invalid");
			return r;
		}

		BinanceOrderTypeType orderType = BinanceOrderTypeType.getType(dto.getOrderTypeCode());
		if (orderType == null) {
			dto.setOrderSideCode(BinanceOrderTypeType.MARKET.getCode());
		} else {
//			if (orderType.equals(BinanceOrderTypeType.LIMIT)) {
//				if (dto.getPrice() == null) {
//					r.failWithMessage("Price invalid");
//					return r;
//				} else if (dto.getTimeInForceCode() == null) {
//					r.failWithMessage("Time in force type invalid");
//					return r;
//				}
//			}
		}
		if (!BinanceOrderTypeType.MARKET.equals(orderType)) {
			dto.setTimeInForceCode(BinanceTimeInForceType.GTC.getCode());
		}

		dto.setTotpCode(genTotpCode());
		cmFutureOrderProducer.binanceCmFutureOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleFutureOrder(CryptoCoinBinanceFutureCmCancelOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		if (StringUtils.isBlank(dto.getSymbol())) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getCancelIfOrderPriceHigherThan() == null) {
			dto.setCancelIfOrderPriceHigherThan(new BigDecimal(Integer.MAX_VALUE));
		}
		dto.setTotpCode(genTotpCode());
		binanceFutureCmCancelOrderProducer.sendCancleOrder(dto);
		r.setIsSuccess();
		return r;
	}
}
