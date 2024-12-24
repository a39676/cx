package demo.finance.cryptoCoin.trading.sevice.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.dto.BaseStrDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmBtcArbitrageWithBatchProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderModifyProducer;
import demo.finance.cryptoCoin.trading.mq.producer.CryptoCoinBinanceUmFutureOrderProducer;
import demo.finance.cryptoCoin.trading.pojo.dto.CryptoCoinBinanceFutureUmGetOrderBySymbolDTO;
import demo.finance.cryptoCoin.trading.pojo.vo.CryptoCoinBinanceFutureUmOpenOrderResponseSubVO;
import demo.finance.cryptoCoin.trading.sevice.CryptoCoinBinanceFutureUmTradingService;
import finance.cryptoCoin.binance.future.um.pojo.dto.BinanceUpdateOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBatchOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.dto.CryptoCoinBinanceFutureUmSetOrderDTO;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceFutureUmQueryOrderResult;
import finance.cryptoCoin.binance.future.um.pojo.result.CryptoCoinBinanceUmFuturePositionInfoResult;
import finance.cryptoCoin.binance.pojo.constant.CcmUrlConstant;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderTypeType;
import finance.cryptoCoin.binance.pojo.type.BinancePositionSideType;
import finance.cryptoCoin.common.pojo.dto.CryptoCoinInteractionCommonDTO;
import finance.cryptoCoin.common.pojo.type.CryptoExchangeType;
import finance.cryptoCoin.pojo.dto.CryptoCoinAddSymbolGroupDTO;
import finance.cryptoCoin.pojo.dto.CryptoCoinSymbolGroupSettingDTO;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CryptoCoinBinanceFutureUmTradingServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinBinanceFutureUmTradingService {

	@Autowired
	private CryptoCoinBinanceUmFutureOrderProducer umFutureOrderProducer;
	@Autowired
	private CryptoCoinBinanceUmBtcArbitrageWithBatchProducer umBtcArbitrageWithBatchProducer;
	@Autowired
	private CryptoCoinBinanceUmFutureOrderModifyProducer umFutureOrderModifyProducer;

	@Override
	public ModelAndView tradingView() {
		ModelAndView v = new ModelAndView("cryptoCoin/setFutureUmOrder");
		v.addObject("title", "BinanceTrading(Future UM)");
		v.addObject("userList", optionService.getUserMetaData());
		v.addObject("exchangeList", CryptoExchangeType.values());

//		LocalDateTime defaultStartTime = LocalDateTime.now().minusHours(8);
//
//		List<CryptoCoinBtcAndLowIndexGapDTO> dataList = complexToolMapper.selectGaps(defaultStartTime);
//		List<String> xValues = new ArrayList<>();
//		List<Double> gap = new ArrayList<>();
//		for (CryptoCoinBtcAndLowIndexGapDTO dto : dataList) {
//			xValues.add(localDateTimeHandler.dateToStr(dto.getStartTime()));
//			gap.add(dto.getGap());
//		}
//
//		v.addObject("xValues", xValues);
//		v.addObject("gap", gap);

		Map<String, String> shortingSymbolDataMap = findSymbolGroupData();
		v.addObject("shortingSymbolData", shortingSymbolDataMap);
		Set<String> allShortingSymbols = new HashSet<>();
		for (Entry<String, String> entry : shortingSymbolDataMap.entrySet()) {
			entry.setValue(entry.getValue().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
			String dataListStr = entry.getValue();
			String[] symbolArray = dataListStr.split(",");
			for (String symbol : symbolArray) {
				allShortingSymbols.add(symbol);
			}
		}
		v.addObject("allShortingSymbols", String.valueOf(allShortingSymbols).replaceAll("\\[", "").replaceAll("\\]", "")
				.replaceAll("\"", "").replaceAll(" ", ""));

		return v;
	}

	@Override
	public CommonResult sendFutureOrder(CryptoCoinBinanceFutureUmBatchOrderDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}
		if (dto.getSymbols() == null || dto.getSymbols().isEmpty()) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		if (dto.getAmount() == null || dto.getAmount() < 0) {
			r.failWithMessage("Amount invalid");
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
				&& dto.getPreOrderRatio() == null) {
			r.failWithMessage("Order ratio invalid");
			return r;
		}

		dto.setTotpCode(genTotpCode());
		umFutureOrderProducer.binanceUmFutureOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult sendBtcArbitrageWithBatchOrder(CryptoCoinBinanceFutureUmBtcArbitrageWithBatchDTO dto) {
		CommonResult r = new CommonResult();
		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}

		if (dto.getSingleAmount() == null || dto.getSingleAmount() < 0) {
			r.failWithMessage("Amount invalid");
			return r;
		}
		if (dto.getSymbols() == null || dto.getSymbols().isEmpty()) {
			r.failWithMessage("Symbol invalid");
			return r;
		}
		dto.setTotpCode(genTotpCode());
		umBtcArbitrageWithBatchProducer.binanceUmBtcArbitrageWithBatch(dto);

		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult batchOrderModify(BinanceUpdateOrderDTO dto) {
		CommonResult r = new CommonResult();
		BinanceOrderSideType orderSide = BinanceOrderSideType.getType(dto.getTargetOrderSideCode());
		BinancePositionSideType positionType = BinancePositionSideType.getType(dto.getTargetPositionSideCode());

		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}

		if (orderSide == null || positionType == null) {
			String msg = "Can NOT update order, " + dto.toString();
			log.error(msg);
			r.failWithMessage(msg);
			return r;
		}

		if (dto.getQuantityRatio() != null && (dto.getQuantityRatio().compareTo(BigDecimal.ZERO) <= 0
				|| dto.getQuantityRatio().compareTo(new BigDecimal(100)) > 0)) {
			String msg = "Can NOT update order, quantity ratio invalid, " + dto.toString();
			log.error(msg);
			r.failWithMessage(msg);
			return r;
		}

		if (dto.getPriceRatio() == null || (dto.getPriceRatio().compareTo(new BigDecimal(-100)) <= 0
				|| dto.getPriceRatio().compareTo(new BigDecimal(100)) > 0)) {
			String msg = "Can NOT update order, price ratio invalid, " + dto.toString();
			log.error(msg);
			r.failWithMessage(msg);
			return r;
		}

		if (StringUtils.isBlank(dto.getOrderId())) {
			dto.setOrderId(null);
		}

		if (dto.getSymbols() != null) {
			for (int i = 0; i < dto.getSymbols().size(); i++) {
				if (StringUtils.isBlank(dto.getSymbols().get(i))) {
					dto.getSymbols().remove(i);
					i--;
				}
			}
		}

		dto.setTotpCode(genTotpCode());
		umFutureOrderModifyProducer.updateOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult closePositionByRatio(CryptoCoinBinanceFutureUmBatchOrderDTO dto) {
		CommonResult r = new CommonResult();

		if (dto.getUserId() == null || StringUtils.isBlank(dto.getUserNickname())) {
			r.failWithMessage("User invalid");
			return r;
		}

		BinanceOrderSideType orderSide = BinanceOrderSideType.getType(dto.getOrderSideCode());
		if (orderSide == null) {
			r.failWithMessage("Order side invalid");
			return r;
		}
		BinancePositionSideType positionSideType = BinancePositionSideType.getType(dto.getPositionSideCode());
		if (positionSideType == null) {
			r.failWithMessage("Position side invalid");
			return r;
		}

		if (!isClosePosition(dto)) {
			r.failWithMessage("Is NOT close position order");
			return r;
		}

		if (dto.getSymbols() == null || dto.getSymbols().isEmpty()) {
			r.failWithMessage("Symbol list invalid");
			return r;
		}
		if (BinanceOrderTypeType.getType(dto.getOrderTypeCode()) == null) {
			r.failWithMessage("Order type invalid; Please set \"LIMIT\" or \"MARKET\"");
			return r;
		}
		if (BinanceOrderTypeType.LIMIT.equals(BinanceOrderTypeType.getType(dto.getOrderTypeCode()))
				&& dto.getPreOrderRatio() == null) {
			r.failWithMessage("Order ratio invalid");
			return r;
		}
		if (dto.getClosePositionQuantityRatio() == null
				|| dto.getClosePositionQuantityRatio().compareTo(BigDecimal.ZERO) < 0
				|| dto.getClosePositionQuantityRatio().compareTo(new BigDecimal(100)) > 100) {
			r.failWithMessage("Quantity ratio invalid");
			return r;
		}

		dto.setTotpCode(genTotpCode());
		umFutureOrderProducer.binanceUmFutureOrder(dto);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult addSymbolGroup(CryptoCoinAddSymbolGroupDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(dto.getSymbolGroupStr())) {
			r.failWithMessage("Empty symbol list");
			return r;
		}
		if (StringUtils.isBlank(dto.getGroupName())) {
			r.failWithMessage("Empty symbol list name");
			return r;
		}

		dto.setSymbolGroupStr(dto.getSymbolGroupStr().replaceAll(" ", ""));

		CryptoCoinSymbolGroupSettingDTO symbolGroupData = getSymbolGroupData();
		for (int i = 0; i < symbolGroupData.getSettings().size(); i++) {
			if (symbolGroupData.getSettings().get(i).getGroupName().equals(dto.getGroupName())) {
				symbolGroupData.getSettings().remove(i);
				i--;
			}
		}

		CryptoCoinAddSymbolGroupDTO newSetting = new CryptoCoinAddSymbolGroupDTO();
		newSetting.setGroupName(dto.getGroupName());
		newSetting.setSymbolGroupStr(dto.getSymbolGroupStr());
		symbolGroupData.getSettings().add(newSetting);

		refreshSymbolGroupData(symbolGroupData);

		r.successWithMessage(dto.getSymbolGroupStr());
		return r;
	}

	@Override
	public CommonResult deleteSymbolGroup(BaseStrDTO dto) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(dto.getStr())) {
			r.failWithMessage("Param invalid");
			return r;
		}

		CryptoCoinSymbolGroupSettingDTO symbolGroupData = getSymbolGroupData();
		if (symbolGroupData.getSettings() == null || symbolGroupData.getSettings().isEmpty()) {
			r.setIsSuccess();
			return r;
		}

		for (int i = 0; i < symbolGroupData.getSettings().size(); i++) {
			CryptoCoinAddSymbolGroupDTO setting = symbolGroupData.getSettings().get(i);
			if (setting.getGroupName().equals(dto.getStr())) {
				symbolGroupData.getSettings().remove(i);
				refreshSymbolGroupData(symbolGroupData);
				r.successWithMessage("Deleted: " + dto.getStr());
				return r;
			}
		}

		r.successWithMessage("Deleted: " + dto.getStr());
		return r;
	}

	@Override
	public CryptoCoinSymbolGroupSettingDTO getSymbolGroupData() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String str = ioUtil.getStringFromFile(OptionFilePathConfigurer.CRYPTO_COIN_SYMBOL_GROUP);
		CryptoCoinSymbolGroupSettingDTO dto = null;
		if (StringUtils.isBlank(str)) {
			dto = new CryptoCoinSymbolGroupSettingDTO();
		} else {
			dto = buildObjFromJsonCustomization(str, CryptoCoinSymbolGroupSettingDTO.class);
		}
		return dto;
	}

	private void refreshSymbolGroupData(CryptoCoinSymbolGroupSettingDTO dto) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(dto);
		FileUtilCustom ioUtil = new FileUtilCustom();
		ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8),
				OptionFilePathConfigurer.CRYPTO_COIN_SYMBOL_GROUP);
	}

	private Map<String, String> findSymbolGroupData() {
		CryptoCoinSymbolGroupSettingDTO symbolGroupData = getSymbolGroupData();
		Map<String, String> symbolListMap = new LinkedHashMap<>();
		if (symbolGroupData.getSettings() == null || symbolGroupData.getSettings().isEmpty()) {
			return symbolListMap;
		}

		for (int i = 0; i < symbolGroupData.getSettings().size(); i++) {
			CryptoCoinAddSymbolGroupDTO setting = symbolGroupData.getSettings().get(i);
			symbolListMap.put(setting.getGroupName(), setting.getSymbolGroupStr());
		}
		return symbolListMap;
	}

	private boolean isClosePosition(CryptoCoinBinanceFutureUmSetOrderDTO dto) {
		return (BinanceOrderSideType.SELL.getCode().equals(dto.getOrderSideCode())
				&& BinancePositionSideType.LONG.getCode().equals(dto.getPositionSideCode()))
				|| (BinanceOrderSideType.BUY.getCode().equals(dto.getOrderSideCode())
						&& BinancePositionSideType.SHORT.getCode().equals(dto.getPositionSideCode()));
	}

	@Override
	public ModelAndView getFutureUmPositionInfo(CryptoCoinInteractionCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getFutureUmPositionTable");
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.POSITION_INFO_UM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		CryptoCoinBinanceUmFuturePositionInfoResult r;
		try {
			String response = h.sendPostRestful(url, json.toString());
			r = buildObjFromJsonCustomization(response, CryptoCoinBinanceUmFuturePositionInfoResult.class);
			if (r == null) {
				v.addObject("msg", "CCM service error");
				return v;
			}
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
	public ModelAndView getFutureUmOpenOrders(CryptoCoinInteractionCommonDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getFutureUmOpenOrdersTable");
		List<CryptoCoinBinanceFutureUmOpenOrderResponseSubVO> list = new ArrayList<>();
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_OPEN_ORDERS_UM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		try {
			String response = h.sendPostRestful(url, json.toString());
			CryptoCoinBinanceFutureUmQueryOrderResult r = buildObjFromJsonCustomization(response,
					CryptoCoinBinanceFutureUmQueryOrderResult.class);
			if (r == null || r.isFail()) {
				return v;
			}
			List<CryptoCoinBinanceFutureUmOrderDTO> orderList = r.getOrderList();
			CryptoCoinBinanceFutureUmOrderDTO orderDTO = null;
			CryptoCoinBinanceFutureUmOpenOrderResponseSubVO dataVO = null;
			for (int i = 0; i < orderList.size(); i++) {
				orderDTO = r.getOrderList().get(i);
				try {
					dataVO = new CryptoCoinBinanceFutureUmOpenOrderResponseSubVO();
					BeanUtils.copyProperties(orderDTO, dataVO);
					dataVO.setOrderTimeStr(localDateTimeHandler.dateToStr(dataVO.getTime()));
					dataVO.setUpdateTimeStr(localDateTimeHandler.dateToStr(dataVO.getUpdateTime()));
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

	@Override
	public ModelAndView getOrdersBySymbol(CryptoCoinBinanceFutureUmGetOrderBySymbolDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getFutureUmOpenOrdersTable");
		List<CryptoCoinBinanceFutureUmOpenOrderResponseSubVO> list = new ArrayList<>();
		HttpUtil h = new HttpUtil();
		String url = optionService.getCcmHost() + CcmUrlConstant.ROOT + CcmUrlConstant.GET_ORDERS_BY_SYMBOL_UM;
		dto.setTotpCode(genTotpCode());
		JSONObject json = JSONObject.fromObject(dto);

		try {
			String response = h.sendPostRestful(url, json.toString());
			CryptoCoinBinanceFutureUmQueryOrderResult r = buildObjFromJsonCustomization(response,
					CryptoCoinBinanceFutureUmQueryOrderResult.class);
			if (r == null || r.isFail()) {
				return v;
			}
			CryptoCoinBinanceFutureUmOrderDTO orderDTO = null;
			CryptoCoinBinanceFutureUmOpenOrderResponseSubVO dataVO = null;
			for (int i = 0; i < r.getOrderList().size(); i++) {
				orderDTO = r.getOrderList().get(i);
				try {
					dataVO = new CryptoCoinBinanceFutureUmOpenOrderResponseSubVO();
					BeanUtils.copyProperties(orderDTO, dataVO);
					dataVO.setOrderTimeStr(localDateTimeHandler.dateToStr(orderDTO.getTime()));
					dataVO.setUpdateTimeStr(localDateTimeHandler.dateToStr(orderDTO.getUpdateTime()));
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
