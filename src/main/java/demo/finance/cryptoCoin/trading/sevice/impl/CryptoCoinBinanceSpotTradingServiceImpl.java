package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinSpotCancelOrderByIdProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinSpotCancelOrderProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinSpotSetOrderProducer;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureCmCancelMultipleOrderMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinSpotSetOrderForMultipleUserDTO;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceSpotOrderVO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceSpotTradingService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotOrderDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotQueryOrdersDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceWalletExtendDetailDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotCancelMultipleOrderDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotCancelOrderByIdDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinSpotSetOrderDTO;
import finance.cryptoCoin.binance.spot.pojo.result.CryptoCoinBinanceSpotAccountInfoResult;
import finance.cryptoCoin.binance.spot.pojo.result.CryptoCoinBinanceSpotOrderListResult;
import finance.cryptoCoin.binance.spot.pojo.result.CryptoCoinBinanceWalletResult;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionSingleUserCommonDTO;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinBinanceSpotTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceSpotTradingService {

	@Autowired
	private CryptoCoinSpotSetOrderProducer spotSetOrderProducer;
	@Autowired
	private CryptoCoinSpotCancelOrderProducer spotCancelOrderProducer;
	@Autowired
	private CryptoCoinSpotCancelOrderByIdProducer spotCancelOrderByIdProducer;

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setSpotOrder");
		v.addObject("title", "BinanceTrading(Spot)");
		v.addObject("userList", optionService.getUserMetaData());
		v.addObject("exchangeList", CryptoExchangeType.values());
		v.addObject("tradingSymbolList", optionService.getTradingSymbolList());

		return v;
	}

	@Override
	public ModelAndView getPositionInfo(CryptoCoinInteractionSingleUserCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getSpotPositionTable");
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.POSITION_INFO_SPOT;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		CryptoCoinBinanceSpotAccountInfoResult r;
		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceSpotAccountInfoResult.class);
			if (r.isFail()) {
				v.addObject("msg", r.getMessage());
				return v;
			}
			v.addObject("accountInfo", r.getInfo());
			v.addObject("userId", dto.getUserId());
			v.addObject("nickname", dto.getUserNickname());
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public ModelAndView getOpenOrders(CryptoCoinInteractionSingleUserCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getSpotOpenOrdersTable");

		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_OPEN_ORDERS_SPOT;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		CryptoCoinBinanceSpotOrderListResult r = null;
		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceSpotOrderListResult.class);
			if (r.isFail()) {
				v.addObject("msg", r.getMessage());
				return v;
			}
			List<CryptoCoinBinanceSpotOrderVO> voList = new ArrayList<>();
			for (int i = 0; i < r.getOrderList().size(); i++) {
				CryptoCoinBinanceSpotOrderVO vo = new CryptoCoinBinanceSpotOrderVO();
				CryptoCoinBinanceSpotOrderDTO order = r.getOrderList().get(i);
				BeanUtils.copyProperties(order, vo);
				vo.setTimeStr(localDateTimeHandler
						.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(order.getTime()))));
				vo.setUpdateTimeStr(localDateTimeHandler
						.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(order.getUpdateTime()))));
				vo.setWorkingTimeStr(localDateTimeHandler
						.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(order.getWorkingTime()))));

				voList.add(vo);
			}
			Collections.sort(voList);
			v.addObject("orderList", voList);
			v.addObject("userId", dto.getUserId());
			v.addObject("userNickname", dto.getUserNickname());
			v.addObject("exchangeCode", dto.getExchangeCode());
			return v;
		} catch (Exception e) {
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public ModelAndView getOrdersBySymbol(CryptoCoinBinanceSpotQueryOrdersDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getSpotOpenOrdersTable");

		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_ORDERS_BY_SYMBOL_SPOT;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		CryptoCoinBinanceSpotOrderListResult r = null;
		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceSpotOrderListResult.class);
			if (r.isFail()) {
				v.addObject("msg", r.getMessage());
				return v;
			}
			List<CryptoCoinBinanceSpotOrderVO> voList = new ArrayList<>();
			for (int i = 0; i < r.getOrderList().size(); i++) {
				CryptoCoinBinanceSpotOrderVO vo = new CryptoCoinBinanceSpotOrderVO();
				CryptoCoinBinanceSpotOrderDTO order = r.getOrderList().get(i);
				BeanUtils.copyProperties(order, vo);
				vo.setTimeStr(localDateTimeHandler
						.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(order.getTime()))));
				vo.setUpdateTimeStr(localDateTimeHandler
						.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(order.getUpdateTime()))));
				vo.setWorkingTimeStr(localDateTimeHandler
						.dateToStr(localDateTimeHandler.dateToLocalDateTime(new Date(order.getWorkingTime()))));

				voList.add(vo);
			}
			v.addObject("orderList", voList);
			return v;
		} catch (Exception e) {
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public ModelAndView getWalletBalance(CryptoCoinInteractionSingleUserCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/walletBalance");

		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_WALLET_BALANCE;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		CryptoCoinBinanceWalletResult r = null;
		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceWalletResult.class);
			if (r.isFail()) {
				v.addObject("msg", r.getMessage());
				return v;
			}

			BigDecimal total = BigDecimal.ZERO;
			BigDecimal totalInUSDT = BigDecimal.ZERO;
			for (int i = 0; i < r.getDetailList().size(); i++) {
				CryptoCoinBinanceWalletExtendDetailDTO detail = r.getDetailList().get(i);
				if (detail.getBalance() != null) {
					total = total.add(detail.getBalance());
					detail.setBalance(detail.getBalance().setScale(SCALE_FOR_PRICE_DISPLAY, RoundingMode.HALF_UP));
					if (detail.getBalanceInUSDT() != null) {
						totalInUSDT = totalInUSDT.add(detail.getBalanceInUSDT());
						detail.setBalanceInUSDT(detail.getBalanceInUSDT().setScale(2, RoundingMode.HALF_UP));
					}
				}
			}

			for (int i = 0; i < r.getDetailList().size(); i++) {
				CryptoCoinBinanceWalletExtendDetailDTO detail = r.getDetailList().get(i);
				if (detail.getBalance() != null) {
					detail.setPercentOfAmountInAccount(
							detail.getBalance().divide(total, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
				} else {
					detail.setPercentOfAmountInAccount(BigDecimal.ZERO);
				}
			}

			CryptoCoinBinanceWalletExtendDetailDTO detail = new CryptoCoinBinanceWalletExtendDetailDTO();
			detail.setActivate(true);
			detail.setWalletName("Total");
			detail.setBalance(total);
			detail.setBalanceInUSDT(totalInUSDT);
			detail.setPercentOfAmountInAccount(new BigDecimal(100));
			r.getDetailList().add(detail);

			v.addObject("detailList", r.getDetailList());
			return v;
		} catch (Exception e) {
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public CommonResult sendOrder(CryptoCoinSpotSetOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.failWithMessage("exchange invalid");
			return r;
		}
		if (dto.getSymbol() == null || dto.getSymbol().isEmpty()) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getQuantity() == null) {
			r.failWithMessage("Quantity invalid");
			return r;
		}
		if (BinanceOrderSideType.getType(dto.getSideCode()) == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}
		if (BinanceOrderTypeType.getType(dto.getTypeCode()) == null) {
			dto.setTypeCode(BinanceOrderTypeType.MARKET.getCode());
		} else if (BinanceOrderTypeType.getType(dto.getTypeCode()).equals(BinanceOrderTypeType.LIMIT)
				&& dto.getPrice() == null) {
			r.failWithMessage("Price invalid");
			return r;
		}
		if (BinanceOrderTypeType.MARKET.getCode().equals(dto.getTypeCode())) {
			dto.setTimeInForceCode(null);
			dto.setPrice(null);
		}
		dto.setTotpCode(genTotpCode());
		if (CryptoExchangeType.BINANCE.equals(exchangeType)) {
			spotSetOrderProducer.setOrder(dto);
			r.setIsSuccess();
			return r;
		} else {
			r.failWithMessage("Other exchange API still dev progress");
			return r;
		}
	}

	@Override
	public CommonResult sendOrderForMultipleUser(CryptoCoinSpotSetOrderForMultipleUserDTO dto) {
		CommonResult r = new CommonResult();
		if (!checkCryptoCoinInteractionMultipleUserCommonDTO(dto)) {
			r.failWithMessage("User invalid");
			return r;
		}
		CryptoExchangeType exchangeType = CryptoExchangeType.getType(dto.getExchangeCode());
		if (exchangeType == null) {
			r.failWithMessage("exchange invalid");
			return r;
		}
		if (!CryptoExchangeType.BINANCE.equals(exchangeType)) {
			r.failWithMessage("Other exchange API still dev progress");
			return r;
		}

		if (dto.getSymbol() == null || dto.getSymbol().isEmpty()) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getQuantity() == null) {
			r.failWithMessage("Quantity invalid");
			return r;
		}
		if (BinanceOrderSideType.getType(dto.getSideCode()) == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}
		if (BinanceOrderTypeType.getType(dto.getTypeCode()) == null) {
			dto.setTypeCode(BinanceOrderTypeType.MARKET.getCode());
		} else if (BinanceOrderTypeType.getType(dto.getTypeCode()).equals(BinanceOrderTypeType.LIMIT)
				&& dto.getPrice() == null) {
			r.failWithMessage("Price invalid");
			return r;
		}
		if (BinanceOrderTypeType.MARKET.getCode().equals(dto.getTypeCode())) {
			dto.setTimeInForceCode(null);
		}

		CryptoCoinSpotSetOrderDTO singleUserDTO = new CryptoCoinSpotSetOrderDTO();
		singleUserDTO.setExchangeCode(dto.getExchangeCode());
		singleUserDTO.setPrice(dto.getPrice());
		singleUserDTO.setQuantity(dto.getQuantity());
		singleUserDTO.setSideCode(dto.getSideCode());
		singleUserDTO.setSymbol(dto.getSymbol());
		singleUserDTO.setTimeInForceCode(singleUserDTO.getTimeInForceCode());
		singleUserDTO.setTypeCode(dto.getTypeCode());
		for (int i = 0; i < dto.getUserIdList().size(); i++) {
			singleUserDTO.setUserId(dto.getUserIdList().get(i));
			singleUserDTO.setUserNickname(dto.getUserNicknameList().get(i));
			singleUserDTO.setTotpCode(genTotpCode());
			spotSetOrderProducer.setOrder(singleUserDTO);
		}
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleMultipleOrder(CryptoCoinSpotCancelMultipleOrderDTO dto) {
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
		spotCancelOrderProducer.cancelMultipleOrder(dto);
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
			CryptoCoinSpotCancelMultipleOrderDTO singleUserDTO = new CryptoCoinSpotCancelMultipleOrderDTO();
			singleUserDTO.setCancelAllOpenOrder(dto.getCancelAllOpenOrder());
			singleUserDTO.setCancelIfOrderPriceHigherThan(dto.getCancelIfOrderPriceHigherThan());
			singleUserDTO.setCancelIfOrderPriceLowerThan(dto.getCancelIfOrderPriceLowerThan());
			singleUserDTO.setExchangeCode(dto.getExchangeCode());
			singleUserDTO.setOrderSideCode(dto.getOrderSideCode());
			singleUserDTO.setOrderTypeCode(dto.getOrderTypeCode());
			singleUserDTO.setSymbol(dto.getSymbol());
			singleUserDTO.setTotpCode(genTotpCode());
			singleUserDTO.setUserId(dto.getUserIdList().get(i));
			singleUserDTO.setUserNickname(dto.getUserNicknameList().get(i));
			spotCancelOrderProducer.cancelMultipleOrder(singleUserDTO);
		}
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult cancleOrderById(CryptoCoinSpotCancelOrderByIdDTO dto) {
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
		spotCancelOrderByIdProducer.cancelOrderById(dto);
		r.setIsSuccess();
		return r;
	}
}
