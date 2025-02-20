package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceFutureCmCancelMultipleOrderProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceFutureCmCancelOrderByIdProducer;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmSetOrderCxDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceFutureCmOpenOrderResponseSubVO;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceFutureCmPositionDetailVO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureCmTradingService;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderByIdDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmOpenOrderResponseSubDTO;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmSetOrderDTO;
import finance.cryptoCoin.binance.future.cm.pojo.result.CryptoCoinBinanceFutureCmQueryOrderResult;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureCmPositionDetailDTO;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceCmFuturePositionInfoResult;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceTimeInForceType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinBinanceFutureCmTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceFutureCmTradingService {

	@Autowired
	private CryptoCoinBinanceCmFutureOrderProducer cmFutureOrderProducer;
	@Autowired
	private CryptoCoinBinanceFutureCmCancelMultipleOrderProducer binanceFutureCmCancelMultipleOrderProducer;
	@Autowired
	private CryptoCoinBinanceFutureCmCancelOrderByIdProducer binanceFutureCmCancelOrderByIdProducer;

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureCmOrder");
		v.addObject("title", "BinanceTrading(Future CM)");
		v.addObject("userList", optionService.getUserMetaData());
		v.addObject("exchangeList", CryptoExchangeType.values());
		v.addObject("tradingSymbolList", optionService.getTradingSymbolList());
		return v;
	}

	@Override
	public ModelAndView getFutureCmPositionInfo(CryptoCoinInteractionSingleUserCommonDTO dto) {
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
			List<CryptoCoinBinanceFutureCmPositionDetailVO> voList = new ArrayList<>();
			for (CryptoCoinBinanceFutureCmPositionDetailDTO positionDTO : r.getPositionList()) {
				CryptoCoinBinanceFutureCmPositionDetailVO vo = new CryptoCoinBinanceFutureCmPositionDetailVO();
				BeanUtils.copyProperties(positionDTO, vo);
				voList.add(vo);
			}
			Collections.sort(voList);
			v.addObject("dataList", voList);
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public ModelAndView getFutureCmOpenOrders(CryptoCoinInteractionSingleUserCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getFutureCmOpenOrdersTable");
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_OPEN_ORDERS_CM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);
		List<CryptoCoinBinanceFutureCmOpenOrderResponseSubVO> openOrderVoList = new ArrayList<>();

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
					openOrderVoList.add(dataVO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			v.addObject("msg", e.getLocalizedMessage());
		}
		Collections.sort(openOrderVoList);
		v.addObject("dataList", openOrderVoList);
		v.addObject("userId", dto.getUserId());
		v.addObject("userNickname", dto.getUserNickname());
		v.addObject("exchangeCode", dto.getExchangeCode());
		return v;
	}

	@Override
	public CommonResult sendFutureOrder(CryptoCoinBinanceFutureCmSetOrderCxDTO dto) {
		if (dto.getOrderRepeatCounting() == null) {
			return sendFutureOrder_(dto);
		}
		CommonResult r = null;
		for (int i = 0; i < dto.getOrderRepeatCounting(); i++) {
			r = sendFutureOrder_(dto);
			if (r.isFail()) {
				return r;
			}
		}
		return r;
	}

	private CommonResult sendFutureOrder_(CryptoCoinBinanceFutureCmSetOrderDTO dto) {
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
		if (BinanceOrderTypeType.MARKET.equals(orderType)) {
			dto.setPrice(null);
		}

		dto.setTotpCode(genTotpCode());
		cmFutureOrderProducer.binanceCmFutureOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult sendFutureOrderForMultipleUser(CryptoCoinBinanceFutureCmSetOrderForMultipleUserDTO dto) {
		if (dto.getOrderRepeatCounting() == null) {
			return sendFutureOrderForMultipleUser_(dto);
		}
		CommonResult r = null;
		for (int i = 0; i < dto.getOrderRepeatCounting(); i++) {
			r = sendFutureOrderForMultipleUser_(dto);
			if (r.isFail()) {
				return r;
			}
		}
		return r;
	}

	private CommonResult sendFutureOrderForMultipleUser_(CryptoCoinBinanceFutureCmSetOrderForMultipleUserDTO dto) {
		CommonResult r = new CommonResult();
		if (!checkCryptoCoinInteractionMultipleUserCommonDTO(dto)) {
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
		if (BinanceOrderTypeType.MARKET.equals(orderType)) {
			dto.setPrice(null);
		}

		CryptoCoinBinanceFutureCmSetOrderDTO singleUserDTO = new CryptoCoinBinanceFutureCmSetOrderDTO();
		singleUserDTO.setExchangeCode(dto.getExchangeCode());
		singleUserDTO.setOrderSideCode(dto.getOrderSideCode());
		singleUserDTO.setOrderTypeCode(dto.getOrderTypeCode());
		singleUserDTO.setPositionSideCode(dto.getPositionSideCode());
		singleUserDTO.setPrice(dto.getPrice());
		singleUserDTO.setQuantity(dto.getQuantity());
		singleUserDTO.setSymbol(dto.getSymbol());
		singleUserDTO.setTimeInForceCode(dto.getTimeInForceCode());
		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			singleUserDTO.setUserId(dto.getUserIdList().get(i));
			singleUserDTO.setUserNickname(dto.getUserNicknameList().get(i));
			singleUserDTO.setTotpCode(genTotpCode());
			cmFutureOrderProducer.binanceCmFutureOrder(singleUserDTO);
		}
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleMultipleOrder(CryptoCoinBinanceFutureCmCancelMultipleOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.failWithMessage("Exchange invalid");
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
		binanceFutureCmCancelMultipleOrderProducer.sendCancleOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleMultipleOrderForMultipleUser(
			CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO dto) {
		CommonResult r = new CommonResult();
		if (!checkCryptoCoinInteractionMultipleUserCommonDTO(dto)) {
			r.failWithMessage("User invalid");
			return r;
		}
		if (StringUtils.isBlank(dto.getSymbol())) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.failWithMessage("Exchange invalid");
			return r;
		}
		if (dto.getCancelIfOrderPriceHigherThan() == null) {
			dto.setCancelIfOrderPriceHigherThan(new BigDecimal(Integer.MAX_VALUE));
		}

		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			CryptoCoinBinanceFutureCmCancelMultipleOrderDTO singleUserDTO = new CryptoCoinBinanceFutureCmCancelMultipleOrderDTO();
			singleUserDTO.setCancelAllOpenOrder(dto.getCancelAllOpenOrder());
			singleUserDTO.setCancelIfOrderPriceHigherThan(dto.getCancelIfOrderPriceHigherThan());
			singleUserDTO.setCancelIfOrderPriceLowerThan(dto.getCancelIfOrderPriceLowerThan());
			singleUserDTO.setExchangeCode(dto.getExchangeCode());
			singleUserDTO.setOrderSideCode(dto.getOrderSideCode());
			singleUserDTO.setOrderTypeCode(dto.getOrderTypeCode());
			singleUserDTO.setPositionSideCode(dto.getPositionSideCode());
			singleUserDTO.setSymbol(dto.getSymbol());
			singleUserDTO.setTotpCode(genTotpCode());
			singleUserDTO.setUserId(dto.getUserIdList().get(i));
			singleUserDTO.setUserNickname(dto.getUserNicknameList().get(i));
			binanceFutureCmCancelMultipleOrderProducer.sendCancleOrder(singleUserDTO);
		}
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleOrderById(CryptoCoinBinanceFutureCmCancelOrderByIdDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.failWithMessage("Exchange invalid");
			return r;
		}
		if (StringUtils.isBlank(dto.getSymbol())) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (StringUtils.isBlank(dto.getOrderId())) {
			r.failWithMessage("Order ID invalid");
			return r;
		}
		dto.setTotpCode(genTotpCode());
		binanceFutureCmCancelOrderByIdProducer.sendCancleOrder(dto);
		r.setIsSuccess();
		return r;
	}
}
