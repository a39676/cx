package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceFutureUmCancelMultipleOrderProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceFutureUmCancelOrderByIdProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceFutureUmCloseAllPositionProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderProducer;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmCloseAllPositionForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2CxDTO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmTradingService;
import finance.cryptoCoin.binance.future.cm.pojo.dto.CryptoCoinBinanceFutureCmCancelOrderByIdDTO;
import finance.cryptoCoin.binance.future.pojo.dto.CryptoCoinBinanceFutureCloseAllPositionDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmCancelMultipleOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2DTO;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceTimeInForceType;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;

@Service
public class CryptoCoinBinanceFutureUmTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceFutureUmTradingService {

	@Autowired
	private CryptoCoinBinanceUmFutureOrderProducer umFutureOrderProducer;
	@Autowired
	private CryptoCoinBinanceFutureUmCancelMultipleOrderProducer binanceFutureUmCancelMultipleOrderProducer;
	@Autowired
	private CryptoCoinBinanceFutureUmCancelOrderByIdProducer binanceFutureUmCancelOrderByIdProducer;
	@Autowired
	private CryptoCoinBinanceFutureUmCloseAllPositionProducer binanceFutureUmCloseAllPositionProducer;

	@Override
	public ModelAndView tradingViewV2() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureUmOrderV2");
		v.addObject("title", "Future UM(Binance) V2");
		v.addObject("userList", optionService.getUserMetaData());
		v.addObject("exchangeList", CryptoExchangeType.values());
		v.addObject("tradingSymbolList", optionService.getTradingSymbolList());
		return v;
	}

	@Override
	public CommonResult sendOrder(CryptoCoinBinanceFutureUmSetOrderV2CxDTO dto) {
		if (dto.getOrderRepeatCounting() == null) {
			return sendOrder_(dto);
		}
		CommonResult r = null;
		for (int i = 0; i < dto.getOrderRepeatCounting(); i++) {
			r = sendOrder_(dto);
			if (r.isFail()) {
				return r;
			}
		}
		return r;
	}

	private CommonResult sendOrder_(CryptoCoinBinanceFutureUmSetOrderV2DTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		if (StringUtils.isBlank(dto.getSymbol())) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getQuantity() == null || dto.getQuantity().doubleValue() < 0) {
			r.failWithMessage("Quantity invalid");
			return r;
		}
		if (BinanceOrderSideType.getType(dto.getOrderSideCode()) == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}
		if (BinancePositionSideType.getType(dto.getPositionSideCode()) == null) {
			r.failWithMessage("Position side invalid");
			return r;
		}
		if (BinanceOrderTypeType.getType(dto.getOrderTypeCode()) == null) {
			dto.setOrderSideCode(BinanceOrderTypeType.MARKET.getCode());
		} else if (BinanceOrderTypeType.getType(dto.getOrderTypeCode()).equals(BinanceOrderTypeType.LIMIT)
				&& dto.getPrice() == null) {
			r.failWithMessage("Price invalid");
			return r;
		}
		if (BinanceOrderTypeType.LIMIT.getCode().equals(dto.getOrderTypeCode())) {
			dto.setTimeInForceCode(BinanceTimeInForceType.GTC.getCode());
		}

		dto.setTotpCode(genTotpCode());
		umFutureOrderProducer.binanceUmFutureOrder(dto);
//		System.out.println(JSONObject.fromObject(dto).toString());
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult sendFutureOrderForMultipleUser(CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO dto) {
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

	private CommonResult sendFutureOrderForMultipleUser_(CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO dto) {
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
		if (BinanceOrderTypeType.MARKET.equals(orderType)) {
			dto.setTimeInForceCode(null);
			dto.setPrice(null);
		}

		CryptoCoinBinanceFutureUmSetOrderV2DTO singleUserDTO = new CryptoCoinBinanceFutureUmSetOrderV2DTO();
		singleUserDTO.setExchangeCode(dto.getExchangeCode());
		singleUserDTO.setOrderSideCode(dto.getOrderSideCode());
		singleUserDTO.setOrderTypeCode(dto.getOrderTypeCode());
		singleUserDTO.setPositionSideCode(dto.getPositionSideCode());
		singleUserDTO.setTimeInForceCode(dto.getTimeInForceCode());
		singleUserDTO.setPrice(dto.getPrice());
		singleUserDTO.setSymbol(dto.getSymbol());
		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			BigDecimal fixedQuantity = umOrderFixQuantityByUserSetting(dto.getUserIdList().get(i),
					dto.getUserNicknameList().get(i), dto.getQuantity());
			if (fixedQuantity.equals(BigDecimal.ZERO)) {
				continue;
			}
			singleUserDTO.setQuantity(fixedQuantity);

			singleUserDTO.setUserId(dto.getUserIdList().get(i));
			singleUserDTO.setUserNickname(dto.getUserNicknameList().get(i));
			singleUserDTO.setTotpCode(genTotpCode());

			umFutureOrderProducer.binanceUmFutureOrder(singleUserDTO);
//			System.out.println(JSONObject.fromObject(singleUserDTO).toString());
		}
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleMultipleOrder(CryptoCoinBinanceFutureUmCancelMultipleOrderDTO dto) {
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
		binanceFutureUmCancelMultipleOrderProducer.sendCancleOrder(dto);
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
			CryptoCoinBinanceFutureUmCancelMultipleOrderDTO singleUserDTO = new CryptoCoinBinanceFutureUmCancelMultipleOrderDTO();
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
			binanceFutureUmCancelMultipleOrderProducer.sendCancleOrder(singleUserDTO);
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
		binanceFutureUmCancelOrderByIdProducer.sendCancleOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult closeAllPosition(CryptoCoinBinanceFutureCloseAllPositionDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		dto.setTotpCode(genTotpCode());
		binanceFutureUmCloseAllPositionProducer.sendCloseAllPosition(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult closeAllPositionForMultipleUser(
			CryptoCoinBinanceFutureUmCloseAllPositionForMultipleUserDTO dto) {
		CommonResult r = new CommonResult();
		if (!checkCryptoCoinInteractionMultipleUserCommonDTO(dto)) {
			r.failWithMessage("User invalid");
			return r;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.failWithMessage("Exchange invalid");
			return r;
		}
		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			CryptoCoinBinanceFutureCloseAllPositionDTO subDTO = new CryptoCoinBinanceFutureCloseAllPositionDTO();
			subDTO.setExchangeCode(dto.getExchangeCode());
			subDTO.setPositionSideCode(dto.getPositionSideCode());
			subDTO.setTotpCode(genTotpCode());
			subDTO.setUserId(dto.getUserIdList().get(i));
			subDTO.setUserNickname(dto.getUserNicknameList().get(i));
			closeAllPosition(subDTO);
		}
		r.setIsSuccess();
		return r;
	}
}
