package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceSpotOrderVO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceSpotTradingService;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceQueryOrdersDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotOrderDTO;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceWalletExtendDetailDTO;
import finance.cryptoCoin.binance.spot.pojo.result.CryptoCoinBinanceSpotAccountInfoResult;
import finance.cryptoCoin.binance.spot.pojo.result.CryptoCoinBinanceSpotOrderListResult;
import finance.cryptoCoin.binance.spot.pojo.result.CryptoCoinBinanceWalletResult;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;

@Service
public class CryptoCoinBinanceSpotTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceSpotTradingService {

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setSpotOrder");
		v.addObject("title", "BinanceTrading(Spot)");
		v.addObject("userList", optionService.getUserMetaData());

		return v;
	}

	@Override
	public ModelAndView getPositionInfo(CryptoCoinInteractionCommonDTO dto) {
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
	public ModelAndView getOpenOrders(CryptoCoinInteractionCommonDTO dto) {
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
			v.addObject("orderList", voList);
			return v;
		} catch (Exception e) {
			v.addObject("msg", e.getLocalizedMessage());
			return v;
		}
	}

	@Override
	public ModelAndView getOrdersBySymbol(CryptoCoinBinanceQueryOrdersDTO dto) {
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
	public ModelAndView getWalletBalance(CryptoCoinInteractionCommonDTO dto) {
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

}