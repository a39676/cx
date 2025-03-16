package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderProducer;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmSetOrderV2CxDTO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmTradingService;
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
		if (!BinanceOrderTypeType.MARKET.equals(orderType)) {
			dto.setTimeInForceCode(BinanceTimeInForceType.GTC.getCode());
		}
		if (BinanceOrderTypeType.MARKET.equals(orderType)) {
			dto.setPrice(null);
		}

		CryptoCoinBinanceFutureUmSetOrderV2DTO singleUserDTO = new CryptoCoinBinanceFutureUmSetOrderV2DTO();
		singleUserDTO.setExchangeCode(dto.getExchangeCode());
		singleUserDTO.setOrderSideCode(dto.getOrderSideCode());
		singleUserDTO.setOrderTypeCode(dto.getOrderTypeCode());
		singleUserDTO.setPositionSideCode(dto.getPositionSideCode());
		singleUserDTO.setPrice(dto.getPrice());
		singleUserDTO.setSymbol(dto.getSymbol());
		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			BigDecimal fixedQuantity = fixQuantityByUserSetting(dto.getUserIdList().get(i),
					dto.getUserNicknameList().get(i), dto.getSymbol(), dto.getQuantity());
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
}
