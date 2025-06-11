package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigForceOrderMapper;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataUrl;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBigTradeQueryDTO;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinForceOrderSummaryDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigForceOrder;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigForceOrderExample;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinBigTradeBubbleChartVO;
import demo.finance.cryptoCoin.data.service.CryptoCoinForceOrderDataService;
import finance.cryptoCoin.binance.pojo.bo.CryptoCoinBinanceFutureUmForceOrderBO;
import finance.cryptoCoin.binance.pojo.bo.CryptoCoinBinanceFutureUmForceOrderDetailBO;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderExecutionType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.pojo.dto.CryptoCoinForceOrderNoticeSettingDTO;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class CryptoCoinForceOrderDataServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinForceOrderDataService {

	@Autowired
	private CryptoCoinBigForceOrderMapper cryptoCoinBigForceOrderMapper;

	private static final String FORCE_ORDER_TOTAL_KEY = "allTotal";

	@Override
	public void checkMostRecentForceOrderSummary() {
		LocalDateTime now = LocalDateTime.now();
		List<CryptoCoinForceOrderNoticeSettingDTO> settings = optionService.getForceOrderNoticeSetting();

		for (CryptoCoinForceOrderNoticeSettingDTO setting : settings) {
			LocalDateTime startTime = now.minusHours(setting.getHourCounting());
			Map<String, CryptoCoinForceOrderSummaryDTO> mostRecentForceOrderSummary = getMostRecentForceOrderSummary(
					startTime);
			if (!mostRecentForceOrderSummary.containsKey(FORCE_ORDER_TOTAL_KEY)) {
				continue;
			}
			CryptoCoinForceOrderSummaryDTO totalData = mostRecentForceOrderSummary.get(FORCE_ORDER_TOTAL_KEY);
			if (setting.getLastNoticeTime() != null
					&& setting.getLastNoticeTime().plusMinutes(setting.getNoticeGapInMinute()).isAfter(now)) {
				continue;
			}

			if (totalData.getForceBuy().add(totalData.getForceSell()).compareTo(setting.getMinimumAmount()) > 0) {
				String msg = "In last " + setting.getHourCounting() + " hours, \n";
				msg += "force buy amount: " + totalData.getForceBuy().setScale(0, RoundingMode.HALF_UP) + ", \n";
				msg += "force sell amount: " + totalData.getForceSell().setScale(0, RoundingMode.HALF_UP) + ",\n";
				msg += " total: "
						+ totalData.getForceBuy().add(totalData.getForceSell()).setScale(0, RoundingMode.HALF_UP)
						+ ",\n";
				if (totalData.getForceBuy().compareTo(totalData.getForceSell()) > 0) {
					msg += "🟢";
				} else {
					msg += "🔴";
				}
				telegramService.sendMessageByChatRecordId(TelegramBotType.CCM_NOTICE, msg, TelegramStaticChatID.MY_ID);
				setting.setLastNoticeTime(now);
			}
		}
	}

	private Map<String, CryptoCoinForceOrderSummaryDTO> getMostRecentForceOrderSummary(LocalDateTime startTime) {
		Map<String, CryptoCoinForceOrderSummaryDTO> summaryMap = new HashMap<>();
		CryptoCoinBigForceOrderExample example = new CryptoCoinBigForceOrderExample();
		example.createCriteria().andEventTimeGreaterThanOrEqualTo(startTime);
		List<CryptoCoinBigForceOrder> dataList = cryptoCoinBigForceOrderMapper.selectByExample(example);
		if (dataList == null || dataList.isEmpty()) {
			return summaryMap;
		}

		CryptoCoinForceOrderSummaryDTO allTotal = new CryptoCoinForceOrderSummaryDTO();
		allTotal.setSymbol(FORCE_ORDER_TOTAL_KEY);

		for (CryptoCoinBigForceOrder data : dataList) {
			CryptoCoinForceOrderSummaryDTO dataInMap = summaryMap.get(data.getSymbol());
			if (dataInMap == null) {
				dataInMap = new CryptoCoinForceOrderSummaryDTO();
				dataInMap.setSymbol(data.getSymbol());
			}
			if (BinanceOrderSideType.BUY.getCode() == data.getOrderSide()) {
				dataInMap.setForceBuy(dataInMap.getForceBuy().add(data.getAmount()));
				dataInMap.setForceBuyCounting(dataInMap.getForceBuyCounting() + 1);
				allTotal.setForceBuy(allTotal.getForceBuy().add(data.getAmount()));
				allTotal.setForceBuyCounting(allTotal.getForceBuyCounting() + 1);
			} else {
				dataInMap.setForceSell(dataInMap.getForceSell().add(data.getAmount()));
				dataInMap.setForceSellCounting(dataInMap.getForceSellCounting() + 1);
				allTotal.setForceSell(allTotal.getForceSell().add(data.getAmount()));
				allTotal.setForceSellCounting(allTotal.getForceSellCounting() + 1);
			}
			summaryMap.put(data.getSymbol(), dataInMap);
		}

		summaryMap.put(FORCE_ORDER_TOTAL_KEY, allTotal);
		return summaryMap;
	}

	@Override
	public ModelAndView getBigForceOrderDataChartBySymbol(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeBubbleChartBySymbol");
		v.addObject("title", "Big force order");
		v.addObject("symbol", dto.getSymbol());

		LocalDateTime now = LocalDateTime.now();
		CryptoCoinBigForceOrderExample example = new CryptoCoinBigForceOrderExample();
		example.createCriteria().andSymbolEqualTo(dto.getSymbol())
				.andEventTimeGreaterThanOrEqualTo(now.minusHours(dto.getStart()))
				.andEventTimeLessThanOrEqualTo(now.minusHours(dto.getEnd()));
		List<CryptoCoinBigForceOrder> dataList = cryptoCoinBigForceOrderMapper.selectByExample(example);

		List<CryptoCoinBigTradeBubbleChartVO> saleList = new ArrayList<>();
		List<CryptoCoinBigTradeBubbleChartVO> buyList = new ArrayList<>();
		CryptoCoinBigForceOrder data = null;
		CryptoCoinBigTradeBubbleChartVO vo = null;
		BigDecimal bigStep = optionService.getBinanceFutureUmSymbolBigStepMap().get(dto.getSymbol());
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			vo = new CryptoCoinBigTradeBubbleChartVO();
			vo.setPrice(data.getPrice().doubleValue());
			Long timeGap = ChronoUnit.MINUTES.between(now, data.getEventTime());
			vo.setTimeGap(timeGap.intValue());
			int r = data.getAmount().divide(bigStep, 0, RoundingMode.HALF_UP).intValue();
			r += 3;
			r *= 3;
			vo.setR(r);
			vo.setAmount(data.getAmount().doubleValue());
			vo.setEventTime(localDateTimeHandler.dateToStr(data.getEventTime()));
			vo.setQuantity(data.getQuantity().doubleValue());

			if (BinanceOrderSideType.SELL.getCode() == data.getOrderSide()) {
				saleList.add(vo);
			} else {
				buyList.add(vo);
			}
		}

		v.addObject("saleList", saleList);
		v.addObject("buyList", buyList);

		return v;
	}

	@Override
	public void receiveNewForceOrderFutureUmDataMessage(String msg) {
		if (StringUtils.isBlank(msg)) {
			return;
		}
		CryptoCoinBinanceFutureUmForceOrderBO bo = buildObjFromJsonCustomization(msg,
				CryptoCoinBinanceFutureUmForceOrderBO.class);
		if (bo.getDetail() == null) {
			return;
		}
		CryptoCoinBinanceFutureUmForceOrderDetailBO detail = bo.getDetail();
		if (StringUtils.isBlank(detail.getSymbol())) {
			return;
		}
		BinanceOrderExecutionType executionType = BinanceOrderExecutionType.getType(detail.getExecutionTypeCode());
		if (!BinanceOrderExecutionType.FILLED.equals(executionType)) {
			// save filled order ONLY
			return;
		}

		CryptoCoinBigForceOrder po = new CryptoCoinBigForceOrder();
		try {
			po.setEventTime(bo.getEventTime());
		} catch (Exception e) {
			return;
		}
		CryptoCoinBigForceOrderExample example = new CryptoCoinBigForceOrderExample();
		example.createCriteria().andSymbolEqualTo(detail.getSymbol()).andEventTimeEqualTo(po.getEventTime())
				.andOrderSideEqualTo(detail.getOrderSideCode()).andQuantityEqualTo(detail.getQuantity())
				.andPriceEqualTo(detail.getOrderPrice());

		List<CryptoCoinBigForceOrder> oldDataList = cryptoCoinBigForceOrderMapper.selectByExample(example);
		if (!oldDataList.isEmpty()) {
			return;
		}

		po.setAmount(detail.getAvgPrice().multiply(detail.getQuantity()));
		po.setPrice(detail.getAvgPrice());
		po.setQuantity(detail.getQuantity());
		po.setOrderSide(detail.getOrderSideCode());
		po.setSymbol(detail.getSymbol());

		try {
			cryptoCoinBigForceOrderMapper.insertSelective(po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ModelAndView getBigForceOrderDataChartBySymbol() {
		ModelAndView v = new ModelAndView("cryptoCoin/bigTradeChartBySymbolView");
		v.addObject("title", "BigForceOrder");
		v.addObject("bubbleChartUrl", CryptoCoinDataUrl.FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL);
//		v.addObject("lineChartUrl", CryptoCoinDataUrl.FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL);
		return v;
	}
}
