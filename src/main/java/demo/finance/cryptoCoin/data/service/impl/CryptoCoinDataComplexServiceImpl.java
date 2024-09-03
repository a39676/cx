package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigForceOrderMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigMoveMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigTradeMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinBigMoveDailySummaryBO;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinBigMoveSummaryBySymbolBO;
import demo.finance.cryptoCoin.data.pojo.constant.CryptoCoinDataUrl;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinBigTradeQueryDTO;
import demo.finance.cryptoCoin.data.pojo.dto.CryptoCoinForceOrderSummaryDTO;
import demo.finance.cryptoCoin.data.pojo.dto.GetBigMoveSummaryDataDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigForceOrder;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigForceOrderExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMove;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMoveExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMoveExample.Criteria;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTrade;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigTradeExample;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinFilterBigMoveDataInTimeRangeResult;
import demo.finance.cryptoCoin.data.pojo.result.GetBigMoveSummaryDataResult;
import demo.finance.cryptoCoin.data.pojo.vo.CryptoCoinBigTradeBubbleChartVO;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;
import finance.cryptoCoin.binance.pojo.bo.CryptoCoinBinanceFutureUmForceOrderBO;
import finance.cryptoCoin.binance.pojo.bo.CryptoCoinBinanceFutureUmForceOrderDetailBO;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderExecutionType;
import finance.cryptoCoin.binance.pojo.type.BinanceOrderSideType;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveDataBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveSummaryDataBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigTradeDataBO;
import finance.cryptoCoin.pojo.dto.CryptoCoinForceOrderNoticeSettingDTO;
import finance.cryptoCoin.pojo.type.CryptoCoinBigMoveDataType;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class CryptoCoinDataComplexServiceImpl extends CryptoCoinCommonService implements CryptoCoinDataComplexService {

	@Autowired
	private CryptoCoinBigMoveMapper cryptoCoinBigMoveMapper;
	@Autowired
	private CryptoCoinBigTradeMapper cryptoCoinBigTradeMapper;
	@Autowired
	private CryptoCoinBigForceOrderMapper cryptoCoinBigForceOrderMapper;

	private static final String FORCE_ORDER_TOTAL_KEY = "allTotal";

	@Override
	public ModelAndView getBigTradeDataBubbleChartBySymbol(String symbol) {
		ModelAndView v = new ModelAndView("cryptoCoin/bigTradeChartBySymbolView");
		v.addObject("title", "Big trade");
		v.addObject("bubbleChartUrl", CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_BUBBLE_CHART_BY_SYMBOL);
		v.addObject("lineChartUrl", CryptoCoinDataUrl.BIG_TRADE_FUTURE_UM_LINE_CHART_BY_SYMBOL);
		if (StringUtils.isNotBlank(symbol)) {
			v.addObject("preSetSymbol", symbol);
			v.addObject("title", "Big trade" + symbol);
		}
		return v;
	}

	@Override
	public ModelAndView getBigTradeBubbleChartBySymbol(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeBubbleChartBySymbol");
		v.addObject("title", (dto.getSymbol() + ", Big trade"));
		v.addObject("symbol", dto.getSymbol());

		LocalDateTime now = LocalDateTime.now();
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		example.createCriteria().andSymbolEqualTo(dto.getSymbol())
				.andEventTimeGreaterThanOrEqualTo(now.minusHours(dto.getStart()))
				.andEventTimeLessThanOrEqualTo(now.minusHours(dto.getEnd()));
		List<CryptoCoinBigTrade> dataList = cryptoCoinBigTradeMapper.selectByExample(example);

		List<CryptoCoinBigTradeBubbleChartVO> saleList = new ArrayList<>();
		List<CryptoCoinBigTradeBubbleChartVO> buyList = new ArrayList<>();
		CryptoCoinBigTrade data = null;
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
			if (data.getIsMaker()) {
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
	public ModelAndView getBigTradeLineChartBySymbol(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeLineChartBySymbol");
		v.addObject("title", (dto.getSymbol() + ", Big trade"));
		v.addObject("symbol", dto.getSymbol());

		LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startTime = now.minusHours(dto.getStart());
		LocalDateTime endTime = now.minusHours(dto.getEnd());
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		example.createCriteria().andSymbolEqualTo(dto.getSymbol()).andEventTimeGreaterThanOrEqualTo(startTime)
				.andEventTimeLessThanOrEqualTo(endTime);
		List<CryptoCoinBigTrade> dataList = cryptoCoinBigTradeMapper.selectByExample(example);

		// for debug
//		BigDecimal total = BigDecimal.ZERO;
//		for (CryptoCoinBigTrade data : dataList) {
//			if (data.getIsMaker()) {
//				total = total.add(data.getAmount().negate());
//			} else {
//				total = total.add(data.getAmount());
//			}
//		}
//		System.out.println(total);

		List<String> timeStringXLine = new ArrayList<>();
		LocalDateTime indexTime = now.minusHours(dto.getStart());
		for (int i = 0; !indexTime.isAfter(endTime); i++) {
			indexTime = startTime.plusHours(i);
			timeStringXLine.add(localDateTimeHandler.dateToStr(indexTime, DATE_FORMAT_FOR_INDEX_CHART_IN_HOUR));
		}

		v.addObject("xValues", timeStringXLine);

		Map<LocalDateTime, CryptoCoinBigTrade> summaryDataMap = new HashMap<>();
		for (int i = 0; i < dataList.size(); i++) {
			CryptoCoinBigTrade dataInList = dataList.get(i);
			CryptoCoinBigTrade dataInMap = summaryDataMap
					.get(dataInList.getEventTime().withMinute(0).withSecond(0).withNano(0));

			if (dataInMap == null) {
				dataInMap = new CryptoCoinBigTrade();
				dataInMap.setIsMaker(dataInList.getIsMaker());
				if (dataInList.getIsMaker()) {
					dataInMap.setAmount(dataInList.getAmount().negate());
				} else {
					dataInMap.setAmount(dataInList.getAmount());
				}
				summaryDataMap.put(dataInList.getEventTime().withMinute(0).withSecond(0).withNano(0), dataInMap);
			} else {
				if (dataInList.getIsMaker()) {
					dataInMap.setAmount(dataInMap.getAmount().subtract(dataInList.getAmount()));
				} else {
					dataInMap.setAmount(dataInMap.getAmount().add(dataInList.getAmount()));
				}
			}
		}

//		for debug
//		total = BigDecimal.ZERO;
//		for(Entry<LocalDateTime, CryptoCoinBigTrade> dataInMap : summaryDataMap.entrySet()) {
//			CryptoCoinBigTrade data = dataInMap.getValue();
//			total = total.add(data.getAmount());
//		}
//		System.out.println(total);

		List<BigDecimal> totalList = new ArrayList<>();
		indexTime = now.minusHours(dto.getStart());
		BigDecimal lastAmount = BigDecimal.ZERO;
		for (int i = 0; !indexTime.isAfter(endTime); i++) {
			indexTime = startTime.plusHours(i);
			CryptoCoinBigTrade dataInMap = summaryDataMap.get(indexTime);
			if (dataInMap == null) {
				totalList.add(lastAmount);
			} else {
				BigDecimal amount = dataInMap.getAmount();
				lastAmount = lastAmount.add(amount);
				totalList.add(lastAmount);
			}
		}

		v.addObject("line", totalList);

		return v;
	}

	@Override
	public ModelAndView getBigForceOrderDataChartBySymbol() {
		ModelAndView v = new ModelAndView("cryptoCoin/bigTradeChartBySymbolView");
		v.addObject("title", "Big force order");
		v.addObject("targetUrl", CryptoCoinDataUrl.BIG_FORCE_ORDER_FUTURE_UM_CHART_BY_SYMBOL);
		return v;
	}

	@Override
	public ModelAndView getBigForceOrderDataChartBySymbol(CryptoCoinBigTradeQueryDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigTradeChartBySymbol");
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
	public void receiveNewBigTradeFutureUmDataMessage(String msg) {
		if (StringUtils.isBlank(msg)) {
			return;
		}
		CryptoCoinBigTradeDataBO bo = buildObjFromJsonCustomization(msg, CryptoCoinBigTradeDataBO.class);
		if (StringUtils.isBlank(bo.getSymbol())) {
			return;
		}
		if (CryptoCoinBigMoveDataType.getType(bo.getDataType()) == null) {
			return;
		}

		CryptoCoinBigTrade po = new CryptoCoinBigTrade();

		try {
			po.setEventTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(bo.getBigTradeTimeStr()));
		} catch (Exception e) {
			return;
		}
		CryptoCoinBigTradeExample example = new CryptoCoinBigTradeExample();
		example.createCriteria().andSymbolEqualTo(bo.getSymbol()).andEventTimeEqualTo(po.getEventTime())
				.andAmountEqualTo(bo.getAmount()).andQuantityEqualTo(bo.getQuantity()).andPriceEqualTo(bo.getPrice())
				.andIsMakerEqualTo(bo.getIsMaker()).andDataTypeEqualTo(bo.getDataType());
		List<CryptoCoinBigTrade> oldDataList = cryptoCoinBigTradeMapper.selectByExample(example);
		if (!oldDataList.isEmpty()) {
			return;
		}

		po.setAmount(bo.getAmount());
		po.setPrice(bo.getPrice());
		po.setQuantity(bo.getQuantity());
		po.setIsMaker(bo.getIsMaker());
		po.setSymbol(bo.getSymbol());
		po.setDataType(bo.getDataType());

		try {
			cryptoCoinBigTradeMapper.insertSelective(po);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveNewBigMoveSpotDataMessage(String msg) {
		if (StringUtils.isBlank(msg)) {
			return;
		}
		CryptoCoinBigMoveDataBO bo = buildObjFromJsonCustomization(msg, CryptoCoinBigMoveDataBO.class);
		if (StringUtils.isBlank(bo.getSymbol())) {
			return;
		}

		CryptoCoinBigMove po = new CryptoCoinBigMove();
		TimeUnitType timeUnit = TimeUnitType.getType(bo.getTimeUnitTypeCode());
		if (timeUnit == null) {
			return;
		}
		try {
			po.setEventTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(bo.getBigMoveTimeStr()));
		} catch (Exception e) {
			return;
		}

		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		example.createCriteria().andSymbolEqualTo(bo.getSymbol()).andRateEqualTo(bo.getRate())
				.andTimeRangeEqualTo(bo.getTimeRange()).andTimeUnitCodeEqualTo(bo.getTimeUnitTypeCode())
				.andEventTimeEqualTo(po.getEventTime()).andVersionEqualTo(bo.getVersion());
		List<CryptoCoinBigMove> oldDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (!oldDataList.isEmpty()) {
			return;
		}

		po.setRate(bo.getRate());
		po.setSymbol(bo.getSymbol());
		po.setTimeRange(bo.getTimeRange());
		po.setTimeUnitCode(bo.getTimeUnitTypeCode());
		po.setVersion(bo.getVersion());
		try {
			cryptoCoinBigMoveMapper.insertSelective(po);
		} catch (DataIntegrityViolationException e) {
			// if duplicate data, skip
		}
	}

	@Override
	public ModelAndView getBigMoveSpotSummaryView() {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigMoveSummaryByManual");
		v.addObject("title", "Big move");

		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneMonthAgo = now.minusMonths(1);
		example.createCriteria().andEventTimeBetween(oneMonthAgo, now).andVersionEqualTo(1);
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}

		CryptoCoinFilterBigMoveDataInTimeRangeResult filterBigMoveDataInTimeRangeResult = filterBigMoveDataInTimeRangeResult(
				bigMoveDataList);

		ModelAndView chartView = handleBigMoveDataChart(v, bigMoveDataList, now.minusDays(7));
		v.addObject("chartView", chartView);

		JSONObject bigDataFilterJson = JSONObject
				.fromObject(filterBigMoveDataInTimeRangeResult.getTodayBigDataFilterResult());
		v.addObject("dataIn24H", bigDataFilterJson);

		bigDataFilterJson = JSONObject.fromObject(filterBigMoveDataInTimeRangeResult.getYesterdayBigDataFilterResult());
		v.addObject("dataIn48H", bigDataFilterJson);

		bigDataFilterJson = JSONObject.fromObject(filterBigMoveDataInTimeRangeResult.getLastweekBigDataFilterResult());
		v.addObject("dataInLastWeek", bigDataFilterJson);

		List<String> risingResultSymbolList = getBigMoveRisingDataCrossResult(filterBigMoveDataInTimeRangeResult);
		v.addObject("risingCrossList", risingResultSymbolList);

		List<String> fallingResultSymbolList = getBigMoveFallingDataCrossResult(filterBigMoveDataInTimeRangeResult);
		v.addObject("fallingCrossList", fallingResultSymbolList);

		return v;
	}

	@Override
	public GetBigMoveSummaryDataResult getBigMoveSummaryDataTable(GetBigMoveSummaryDataDTO dto) {
		GetBigMoveSummaryDataResult r = new GetBigMoveSummaryDataResult();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startTime = now.minusHours(dto.getHourRangeStart());
		LocalDateTime endTime = now.minusHours(dto.getHourRangeEnd());

		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		Criteria criteria = example.createCriteria();
		criteria.andEventTimeBetween(startTime, endTime).andVersionEqualTo(dto.getVersion());
		if (StringUtils.isNotBlank(dto.getSymbols())) {
			List<String> symbolList = new ArrayList<>();
			symbolList.addAll(Arrays.asList(dto.getSymbols().split(",")));
			criteria.andSymbolIn(symbolList);
		}
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);

		r = buildSummaryListByStartTimeRange(bigMoveDataList, startTime, endTime);
		return r;
	}

	private GetBigMoveSummaryDataResult buildSummaryListByStartTimeRange(List<CryptoCoinBigMove> bigMoveDataList,
			LocalDateTime startTime) {
		LocalDateTime endTime = LocalDateTime.now();
		return buildSummaryListByStartTimeRange(bigMoveDataList, startTime, endTime);
	}

	private GetBigMoveSummaryDataResult buildSummaryListByStartTimeRange(List<CryptoCoinBigMove> bigMoveDataList,
			LocalDateTime startTime, LocalDateTime endTime) {
		GetBigMoveSummaryDataResult r = new GetBigMoveSummaryDataResult();
		List<CryptoCoinBigMoveSummaryDataBO> summaryList = new ArrayList<>();
		Map<String, CryptoCoinBigMoveSummaryDataBO> summaryMap = new HashMap<>();
		for (CryptoCoinBigMove po : bigMoveDataList) {
			if (po.getEventTime().isBefore(startTime) || po.getEventTime().isAfter(endTime)) {
				continue;
			}
			CryptoCoinBigMoveSummaryDataBO summaryBO = summaryMap.get(po.getSymbol());
			if (summaryBO == null) {
				summaryBO = new CryptoCoinBigMoveSummaryDataBO();
				summaryBO.setSymbol(po.getSymbol());
			}
			summaryBO.setTotalCounter(summaryBO.getTotalCounter() + 1);
			summaryBO.setTotalRate(summaryBO.getTotalRate().add(po.getRate()));
			if (po.getRate().compareTo(BigDecimal.ZERO) > 0) {
				summaryBO.setRiseCounter(summaryBO.getRiseCounter() + 1);
				summaryBO.setTotalRiseRate(summaryBO.getTotalRiseRate().add(po.getRate()));
			} else {
				summaryBO.setFallCounter(summaryBO.getFallCounter() + 1);
				summaryBO.setTotalFallRate(summaryBO.getTotalFallRate().add(po.getRate()));
			}
			summaryMap.put(summaryBO.getSymbol(), summaryBO);
		}

		if (summaryMap.isEmpty()) {
			r.setMessage("Can NOT find any big move data between: " + localDateTimeHandler.dateToStr(startTime) + " - "
					+ localDateTimeHandler.dateToStr(endTime));
			r.setDataList(summaryList);
			r.setIsSuccess();
			return r;
		}

		summaryList.addAll(summaryMap.values());
		Collections.sort(summaryList);
		r.setDataList(summaryList);

		r.setTotal(summaryList.size());
		for (CryptoCoinBigMoveSummaryDataBO data : summaryList) {
			if (data.getTotalRate().compareTo(BigDecimal.ZERO) > 0) {
				r.setRisingCounting(r.getRisingCounting() + 1);
			} else if (data.getTotalRate().compareTo(BigDecimal.ZERO) < 0) {
				r.setFallingCounting(r.getFallingCounting() + 1);
			}
		}
		r.setIsSuccess();
		return r;
	}

	@Override
	public ModelAndView getBigMoveDataChart(GetBigMoveSummaryDataDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigMoveChart");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startTime = now.minusHours(dto.getHourRangeStart());
		LocalDateTime endTime = now.minusHours(dto.getHourRangeEnd());
		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		example.createCriteria().andEventTimeBetween(startTime, endTime).andVersionEqualTo(dto.getVersion());
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}

		return handleBigMoveDataChart(v, bigMoveDataList, startTime);
	}

	private ModelAndView handleBigMoveDataChart(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList,
			LocalDateTime startTime) {
		if (v == null) {
			v = new ModelAndView("cryptoCoin/getBigMoveChart");
		}
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		Map<Long, CryptoCoinBigMoveDailySummaryBO> countingMap = bigMoveDataListToCountingMap(bigMoveDataList,
				startTime);
		List<CryptoCoinBigMoveDailySummaryBO> resultList = new ArrayList<>();
		resultList.addAll(countingMap.values());
		Collections.sort(resultList);

		List<String> xValueList = new ArrayList<>();
		List<Integer> total = new ArrayList<>();
		List<Integer> mainCounting = new ArrayList<>();
		List<Integer> otherCounting = new ArrayList<>();
		List<Integer> mainTotalList = new ArrayList<>();
		List<Integer> otherTotalList = new ArrayList<>();
		List<Integer> mainSummaryCounting = new ArrayList<>();
		List<Integer> mainRisingCounting = new ArrayList<>();
		List<Integer> mainFallingCounting = new ArrayList<>();
		List<Integer> otherSummaryCounting = new ArrayList<>();
		List<Integer> otherRisingCounting = new ArrayList<>();
		List<Integer> otherFallingCounting = new ArrayList<>();
		Integer mainTotal = 0;
		Integer otherTotal = 0;
		for (CryptoCoinBigMoveDailySummaryBO data : resultList) {
			xValueList.add(data.getStartTimeStr());
			total.add(data.getTotal());
			mainCounting.add(data.getMainCounting());
			mainTotal = Integer.valueOf(mainTotal + data.getMainRisingCounting() + data.getMainFallingCounting());
			mainTotalList.add(mainTotal);
			otherTotal = Integer.valueOf(otherTotal + data.getOtherRisingCounting() + data.getOtherFallingCounting());
			otherTotalList.add(otherTotal);
			otherCounting.add(data.getOtherCounting());
			mainSummaryCounting.add(data.getMainSummaryCounting());
			mainRisingCounting.add(data.getMainRisingCounting());
			mainFallingCounting.add(data.getMainFallingCounting());
			otherSummaryCounting.add(data.getOtherSummaryCounting());
			otherRisingCounting.add(data.getOtherRisingCounting());
			otherFallingCounting.add(data.getOtherFallingCounting());
		}

		v.addObject("xValues", xValueList);
		v.addObject("total", total);
		v.addObject("mainCounting", mainCounting);
		v.addObject("mainTotalList", mainTotalList);
		v.addObject("otherCounting", otherCounting);
		v.addObject("otherTotalList", otherTotalList);
		v.addObject("mainSummaryCounting", mainSummaryCounting);
		v.addObject("mainRisingCounting", mainRisingCounting);
		v.addObject("mainFallingCounting", mainFallingCounting);
		v.addObject("otherSummaryCounting", otherSummaryCounting);
		v.addObject("otherRisingCounting", otherRisingCounting);
		v.addObject("otherFallingCounting", otherFallingCounting);

		return v;
	}

	@Override
	public ModelAndView getBigMoveDataChartBySymbol(GetBigMoveSummaryDataDTO dto) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigMoveChartBySymbol");
		if (StringUtils.isBlank(dto.getSymbols())) {
			return v;
		}
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startTime = now.minusHours(dto.getHourRangeStart());
		LocalDateTime endTime = now.minusHours(dto.getHourRangeEnd());
		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		example.createCriteria().andEventTimeBetween(startTime, endTime).andSymbolEqualTo(dto.getSymbols())
				.andVersionEqualTo(dto.getVersion());
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}

		return handleBigMoveDataChartBySymbol(v, bigMoveDataList, startTime);
	}

	private ModelAndView handleBigMoveDataChartBySymbol(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList,
			LocalDateTime startTime) {
		if (v == null) {
			v = new ModelAndView("cryptoCoin/getBigMoveChartBySymbol");
		}
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		Map<Long, CryptoCoinBigMoveSummaryBySymbolBO> countingMap = bigMoveDataListToCountingMapBySymbol(
				bigMoveDataList, startTime);
		List<CryptoCoinBigMoveSummaryBySymbolBO> resultList = new ArrayList<>();
		resultList.addAll(countingMap.values());
		Collections.sort(resultList);

		List<String> xValueList = new ArrayList<>();
		List<Integer> raisingCounting = new ArrayList<>();
		List<Integer> fallingCounting = new ArrayList<>();
		List<Integer> countingList = new ArrayList<>();
		List<BigDecimal> ratioList = new ArrayList<>();
		Integer counting = 0;
		BigDecimal ratioSummary = BigDecimal.ZERO;
		for (CryptoCoinBigMoveSummaryBySymbolBO data : resultList) {
			xValueList.add(data.getStartTimeStr());
			counting = Integer.valueOf(counting + data.getCounting());
			countingList.add(counting);
			raisingCounting.add(data.getRisingCounting());
			fallingCounting.add(data.getFallingCounting());
			ratioSummary = ratioSummary.add(data.getSummaryRatio());
			ratioList.add(ratioSummary);
		}

		v.addObject("xValues", xValueList);
		v.addObject("countingList", countingList);
		v.addObject("raisingCounting", raisingCounting);
		v.addObject("fallingCounting", fallingCounting);
		v.addObject("ratioList", ratioList);

		return v;
	}

	private Map<Long, CryptoCoinBigMoveDailySummaryBO> bigMoveDataListToCountingMap(
			List<CryptoCoinBigMove> bigMoveDataList, LocalDateTime startTime) {
		Map<Long, CryptoCoinBigMoveDailySummaryBO> countingMap = new HashMap<>();
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return countingMap;
		}
		CryptoCoinBigMoveDailySummaryBO tmpBO = null;
		LocalDateTime now = LocalDateTime.now();
		if (startTime == null) {
			startTime = now.minusDays(14);
		}

		Long hourGap = ChronoUnit.HOURS.between(startTime, now);
		LocalDateTime tmpTime = null;
		for (Long i = 0L; i < hourGap; i++) {
			tmpTime = now.minusHours(i);
			tmpBO = new CryptoCoinBigMoveDailySummaryBO();
			tmpBO.setStartTime(tmpTime);
			tmpBO.setStartTimeStr(localDateTimeHandler.dateToStr(tmpBO.getStartTime(), "MM-dd HH:mm"));
			countingMap.put(i, tmpBO);
		}

		for (int i = 0; i < bigMoveDataList.size(); i++) {
			CryptoCoinBigMove data = bigMoveDataList.get(i);
			if (data.getEventTime().isBefore(startTime)) {
				continue;
			}
			hourGap = ChronoUnit.HOURS.between(data.getEventTime(), now);
			if (countingMap.containsKey(hourGap)) {
				tmpBO = countingMap.get(hourGap);
			} else {
				tmpBO = new CryptoCoinBigMoveDailySummaryBO();
				tmpBO.setStartTime(now.minusHours(hourGap));
				tmpBO.setStartTimeStr(localDateTimeHandler.dateToStr(tmpBO.getStartTime(), "MM-dd HH:mm"));
			}
			tmpBO.setTotal(tmpBO.getTotal() + 1);
			if (optionService.getBinanceMainList().contains(data.getSymbol())) {
				tmpBO.setMainCounting(tmpBO.getMainCounting() + 1);
				if (data.getRate().compareTo(BigDecimal.ZERO) > 0) {
					tmpBO.setMainRisingCounting(tmpBO.getMainRisingCounting() + 1);
					tmpBO.setMainSummaryCounting(tmpBO.getMainSummaryCounting() + 1);
				} else {
					tmpBO.setMainFallingCounting(tmpBO.getMainFallingCounting() - 1);
					tmpBO.setMainSummaryCounting(tmpBO.getMainSummaryCounting() - 1);
				}
			} else {
				tmpBO.setOtherCounting(tmpBO.getOtherCounting() + 1);
				if (data.getRate().compareTo(BigDecimal.ZERO) > 0) {
					tmpBO.setOtherRisingCounting(tmpBO.getOtherRisingCounting() + 1);
					tmpBO.setOtherSummaryCounting(tmpBO.getOtherSummaryCounting() + 1);
				} else {
					tmpBO.setOtherFallingCounting(tmpBO.getOtherFallingCounting() - 1);
					tmpBO.setOtherSummaryCounting(tmpBO.getOtherSummaryCounting() - 1);
				}
			}
			countingMap.put(hourGap, tmpBO);
		}

		return countingMap;
	}

	private Map<Long, CryptoCoinBigMoveSummaryBySymbolBO> bigMoveDataListToCountingMapBySymbol(
			List<CryptoCoinBigMove> bigMoveDataList, LocalDateTime startTime) {
		Map<Long, CryptoCoinBigMoveSummaryBySymbolBO> countingMap = new HashMap<>();
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return countingMap;
		}
		CryptoCoinBigMoveSummaryBySymbolBO tmpBO = null;
		LocalDateTime now = LocalDateTime.now();

		Long hourGap = 0L;
		LocalDateTime tmpTime = now.minusHours(hourGap);
		for (; tmpTime.isAfter(startTime);) {
			tmpBO = new CryptoCoinBigMoveSummaryBySymbolBO();
			tmpBO.setStartTime(tmpTime);
			tmpBO.setStartTimeStr(localDateTimeHandler.dateToStr(tmpBO.getStartTime(), "MM-dd HH:mm"));
			countingMap.put(hourGap, tmpBO);
			hourGap++;
			tmpTime = now.minusHours(hourGap);
		}

		for (int i = 0; i < bigMoveDataList.size(); i++) {
			CryptoCoinBigMove data = bigMoveDataList.get(i);
			if (data.getEventTime().isBefore(startTime)) {
				continue;
			}
			hourGap = ChronoUnit.HOURS.between(data.getEventTime(), now);
			tmpBO = countingMap.get(hourGap);
			if (data.getRate().compareTo(BigDecimal.ZERO) > 0) {
				tmpBO.setRisingCounting(tmpBO.getRisingCounting() + 1);
				tmpBO.setCounting(tmpBO.getCounting() + 1);
			} else {
				tmpBO.setFallingCounting(tmpBO.getFallingCounting() - 1);
				tmpBO.setCounting(tmpBO.getCounting() - 1);
			}
			tmpBO.setSummaryRatio(tmpBO.getSummaryRatio().add(data.getRate()));
			countingMap.put(hourGap, tmpBO);
			tmpBO = null;
		}

		return countingMap;
	}

	public CryptoCoinFilterBigMoveDataInTimeRangeResult filterBigMoveDataInTimeRangeResult(
			List<CryptoCoinBigMove> bigMoveDataList) {
		CryptoCoinFilterBigMoveDataInTimeRangeResult r = new CryptoCoinFilterBigMoveDataInTimeRangeResult();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime yesterDayEnd = now.minusHours(24);
		LocalDateTime yesterDayStart = now.minusHours(48);
		LocalDateTime thisWeekStart = now.minusDays(7);

		GetBigMoveSummaryDataResult todayBigDataFilterResult = buildSummaryListByStartTimeRange(bigMoveDataList,
				yesterDayEnd);

		GetBigMoveSummaryDataResult yesterdayBigDataFilterResult = buildSummaryListByStartTimeRange(bigMoveDataList,
				yesterDayStart, yesterDayEnd);

		GetBigMoveSummaryDataResult lastweekBigDataFilterResult = buildSummaryListByStartTimeRange(bigMoveDataList,
				thisWeekStart);

		r.setTodayBigDataFilterResult(todayBigDataFilterResult);
		r.setYesterdayBigDataFilterResult(yesterdayBigDataFilterResult);
		r.setLastweekBigDataFilterResult(lastweekBigDataFilterResult);

		r.setIsSuccess();
		return r;
	}

	private List<String> getBigMoveRisingDataCrossResult(CryptoCoinFilterBigMoveDataInTimeRangeResult bigMoveData) {
		List<String> result = new ArrayList<>();
		if (bigMoveData.getTodayBigDataFilterResult().getDataList().isEmpty()
				|| bigMoveData.getYesterdayBigDataFilterResult().getDataList().isEmpty()) {
			return result;
		}

		int minRisingCounting = 4;

		List<CryptoCoinBigMoveSummaryDataBO> today = bigMoveData.getTodayBigDataFilterResult().getDataList();
		List<CryptoCoinBigMoveSummaryDataBO> yesterday = bigMoveData.getYesterdayBigDataFilterResult().getDataList();

		List<String> yesterdaySymbolList = new ArrayList<>();
		for (int i = 0; i < yesterday.size(); i++) {
			CryptoCoinBigMoveSummaryDataBO data = yesterday.get(i);
			if (data.getFallCounter() >= minRisingCounting && data.getTotalRate().compareTo(BigDecimal.ZERO) > 0) {
				yesterdaySymbolList.add(data.getSymbol());
			}
		}
		if (yesterdaySymbolList.isEmpty()) {
			return result;
		}

		List<String> todaySymbolList = new ArrayList<>();
		for (int i = 0; i < today.size(); i++) {
			CryptoCoinBigMoveSummaryDataBO data = today.get(i);
			if (data.getFallCounter() >= minRisingCounting && yesterdaySymbolList.contains(data.getSymbol())
					&& data.getTotalRate().compareTo(BigDecimal.ZERO) > 0) {
				todaySymbolList.add(data.getSymbol());
			}
		}
		if (todaySymbolList.isEmpty()) {
			return result;
		}

		List<CryptoCoinBigMoveSummaryDataBO> thisWeek = bigMoveData.getLastweekBigDataFilterResult().getDataList();
		for (int i = 0; i < thisWeek.size(); i++) {
			CryptoCoinBigMoveSummaryDataBO data = thisWeek.get(i);
			if (data.getFallCounter() >= minRisingCounting && todaySymbolList.contains(data.getSymbol())
					&& data.getTotalRate().compareTo(BigDecimal.ZERO) > 0) {
				result.add(data.getSymbol());
			}
		}

		return result;
	}

	private List<String> getBigMoveFallingDataCrossResult(CryptoCoinFilterBigMoveDataInTimeRangeResult bigMoveData) {
		List<String> result = new ArrayList<>();
		if (bigMoveData.getTodayBigDataFilterResult().getDataList().isEmpty()
				|| bigMoveData.getYesterdayBigDataFilterResult().getDataList().isEmpty()) {
			return result;
		}

		int minFallingCounting = 4;

		List<CryptoCoinBigMoveSummaryDataBO> today = bigMoveData.getTodayBigDataFilterResult().getDataList();
		List<CryptoCoinBigMoveSummaryDataBO> yesterday = bigMoveData.getYesterdayBigDataFilterResult().getDataList();

		List<String> yesterdaySymbolList = new ArrayList<>();
		for (int i = 0; i < yesterday.size(); i++) {
			CryptoCoinBigMoveSummaryDataBO data = yesterday.get(i);
			if (data.getFallCounter() >= minFallingCounting && data.getTotalRate().compareTo(BigDecimal.ZERO) < 0) {
				yesterdaySymbolList.add(data.getSymbol());
			}
		}
		if (yesterdaySymbolList.isEmpty()) {
			return result;
		}

		List<String> todaySymbolList = new ArrayList<>();
		for (int i = 0; i < today.size(); i++) {
			CryptoCoinBigMoveSummaryDataBO data = today.get(i);
			if (data.getFallCounter() >= minFallingCounting && yesterdaySymbolList.contains(data.getSymbol())
					&& data.getTotalRate().compareTo(BigDecimal.ZERO) < 0) {
				todaySymbolList.add(data.getSymbol());
			}
		}
		if (todaySymbolList.isEmpty()) {
			return result;
		}

		List<CryptoCoinBigMoveSummaryDataBO> thisWeek = bigMoveData.getLastweekBigDataFilterResult().getDataList();
		for (int i = 0; i < thisWeek.size(); i++) {
			CryptoCoinBigMoveSummaryDataBO data = thisWeek.get(i);
			if (data.getFallCounter() >= minFallingCounting && todaySymbolList.contains(data.getSymbol())
					&& data.getTotalRate().compareTo(BigDecimal.ZERO) < 0) {
				result.add(data.getSymbol());
			}
		}

		return result;
	}

	@Override
	public void sendBigMoveDataCrossResult() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneWeekAgo = now.minusDays(7);
		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		example.createCriteria().andEventTimeBetween(oneWeekAgo, now);
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		CryptoCoinFilterBigMoveDataInTimeRangeResult filterBigMoveDataInTimeRangeResult = filterBigMoveDataInTimeRangeResult(
				bigMoveDataList);
		List<String> risingResult = getBigMoveRisingDataCrossResult(filterBigMoveDataInTimeRangeResult);
//		JSONArray ja = JSONArray.fromObject(result);
//		cryptoCoinSetOrderProducer.sendCryptoCoinDailyDataQueryForTest(ja.toString());

		List<String> fallingResult = getBigMoveFallingDataCrossResult(filterBigMoveDataInTimeRangeResult);

		String msg = "Big move data rising cross result: " + String.valueOf(risingResult) + ", \n"
				+ "falling cross result: " + fallingResult;
		telegramService.sendMessageByChatRecordId(TelegramBotType.NORMAL_MSG, msg, TelegramStaticChatID.MY_ID);
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
			po.setEventTime(localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(bo.getEventName()));
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
	public void checkMostRecentForceOrderSummary() {
		LocalDateTime now = LocalDateTime.now();
		List<CryptoCoinForceOrderNoticeSettingDTO> settings = optionService.getForceOrderNoticeSetting();

		for (CryptoCoinForceOrderNoticeSettingDTO setting : settings) {
			LocalDateTime startTime = now.minusHours(setting.getHourCounting());
			Map<String, CryptoCoinForceOrderSummaryDTO> mostRecentForceOrderSummary = getMostRecentForceOrderSummary(
					startTime);
			CryptoCoinForceOrderSummaryDTO totalData = mostRecentForceOrderSummary.get(FORCE_ORDER_TOTAL_KEY);
			if (setting.getLastNoticeTime() != null
					&& setting.getLastNoticeTime().plusMinutes(setting.getNoticeGapInMinute()).isAfter(now)) {
				continue;
			}

			if (totalData.getForceBuy().add(totalData.getForceSell()).compareTo(setting.getMinimumAmount()) > 0) {
				String msg = "In last " + setting.getHourCounting() + " hours, force buy amount: "
						+ totalData.getForceBuy() + ", force sell amount: " + totalData.getForceSell() + ",\n"
						+ " total: " + totalData.getForceBuy().add(totalData.getForceSell());
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
}
