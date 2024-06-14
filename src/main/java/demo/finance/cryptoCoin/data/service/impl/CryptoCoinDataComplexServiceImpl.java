package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
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
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigMoveMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinBigMoveDailySummaryBO;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinBigMoveSummaryBySymbolBO;
import demo.finance.cryptoCoin.data.pojo.dto.GetBigMoveSummaryDataDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMove;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMoveExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMoveExample.Criteria;
import demo.finance.cryptoCoin.data.pojo.result.CryptoCoinFilterBigMoveDataInTimeRangeResult;
import demo.finance.cryptoCoin.data.pojo.result.GetBigMoveSummaryDataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;
import demo.finance.cryptoCoin.mq.producer.CryptoCoinSetOrderProducer;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveDataBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveSummaryDataBO;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class CryptoCoinDataComplexServiceImpl extends CryptoCoinCommonService implements CryptoCoinDataComplexService {

	@Autowired
	private CryptoCoinBigMoveMapper cryptoCoinBigMoveMapper;
	@SuppressWarnings("unused")
	@Autowired
	private CryptoCoinSetOrderProducer cryptoCoinSetOrderProducer;

	@Override
	public void receiveNewBigMoveDataMessage(String msg) {
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
				.andEventTimeEqualTo(po.getEventTime());
		List<CryptoCoinBigMove> oldDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (!oldDataList.isEmpty()) {
			return;
		}

		po.setRate(bo.getRate());
		po.setSymbol(bo.getSymbol());
		po.setTimeRange(bo.getTimeRange());
		po.setTimeUnitCode(bo.getTimeUnitTypeCode());
		try {
			cryptoCoinBigMoveMapper.insertSelective(po);
		} catch (DataIntegrityViolationException e) {
			// if duplicate data, skip
		}
	}

	@Override
	public ModelAndView getBigMoveSummaryView() {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigMoveSummaryByManual");
		v.addObject("title", "getBigMoveSummaryByManual");

		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneMonthAgo = now.minusMonths(1);
		example.createCriteria().andEventTimeBetween(oneMonthAgo, now);
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}

		CryptoCoinFilterBigMoveDataInTimeRangeResult filterBigMoveDataInTimeRangeResult = filterBigMoveDataInTimeRangeResult(
				bigMoveDataList);

		v = handleRecentBigMoveDataSummary(v, bigMoveDataList);
		ModelAndView chartView = handleBigMoveDataChart(v, bigMoveDataList, 14);
		v.addObject("chartView", chartView);

		JSONObject bigDataFilterJson = JSONObject
				.fromObject(filterBigMoveDataInTimeRangeResult.getTodayBigDataFilterResult());
		v.addObject("dataIn24H", bigDataFilterJson);

		bigDataFilterJson = JSONObject.fromObject(filterBigMoveDataInTimeRangeResult.getYesterdayBigDataFilterResult());
		v.addObject("dataIn48H", bigDataFilterJson);

		bigDataFilterJson = JSONObject.fromObject(filterBigMoveDataInTimeRangeResult.getLastweekBigDataFilterResult());
		v.addObject("dataInLastWeek", bigDataFilterJson);

		List<String> resultSymbolList = getBigMoveRisingDataCrossResult(filterBigMoveDataInTimeRangeResult);
		v.addObject("crossList", resultSymbolList);

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
		criteria.andEventTimeBetween(startTime, endTime);
		if (StringUtils.isNotBlank(dto.getSymbols())) {
			List<String> symbolList = new ArrayList<>();
			symbolList.addAll(Arrays.asList(dto.getSymbols().split(",")));
			criteria.andSymbolIn(symbolList);
		}
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);

		r = buildSummaryListByStartTimeRange(bigMoveDataList, startTime, endTime);
		return r;
	}

	private ModelAndView handleRecentBigMoveDataSummary(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList) {
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		LocalDateTime tenMinAgo = LocalDateTime.now().minusMinutes(10);
		List<String> timingKeyList = new ArrayList<>();
		Map<String, List<CryptoCoinBigMoveDataBO>> recentDataMap = new HashMap<>();
		for (CryptoCoinBigMove po : bigMoveDataList) {
			if (po.getEventTime().isBefore(tenMinAgo)) {
				continue;
			}
			String timingKey = getTimingKey(po);
			if (!timingKeyList.contains(timingKey)) {
				timingKeyList.add(timingKey);
			}
			CryptoCoinBigMoveDataBO bo = poToBo(po);

			List<CryptoCoinBigMoveDataBO> subDataList = recentDataMap.get(timingKey);
			if (subDataList == null) {
				subDataList = new ArrayList<>();
				recentDataMap.put(timingKey, subDataList);
			}
			subDataList.add(bo);
		}

		v.addObject("recentTimeKeyList", timingKeyList);
		if (recentDataMap.isEmpty()) {
			v.addObject("recentBigMoveMsg", "Can NOT found any recent big move");
		}

		for (String key : recentDataMap.keySet()) {
			Collections.sort(recentDataMap.get(key));
		}

		v.addObject("recentDataMap", recentDataMap);
		return v;
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
				summaryBO.setRiseCounter(summaryBO.getFallCounter() + 1);
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
	public ModelAndView getBigMoveDataChart(Integer days) {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigMoveChart");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startDay = now.minusDays(days);
		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		example.createCriteria().andEventTimeBetween(startDay, now);
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}

		return handleBigMoveDataChart(v, bigMoveDataList, days);
	}

	private ModelAndView handleBigMoveDataChart(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList, Integer days) {
		if (v == null) {
			v = new ModelAndView("cryptoCoin/getBigMoveChart");
		}
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		if (days == null) {
			days = 14;
		}
		LocalDateTime startTime = LocalDateTime.now().minusDays(days);
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
		example.createCriteria().andEventTimeBetween(startTime, endTime).andSymbolEqualTo(dto.getSymbols());
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
		Long hourGap = null;
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

		Long hourGap = ChronoUnit.HOURS.between(startTime, now);
		for (Long i = 0L; i < hourGap; i++) {
			tmpBO = new CryptoCoinBigMoveSummaryBySymbolBO();
			tmpBO.setStartTime(startTime.plusHours(i));
			tmpBO.setStartTimeStr(localDateTimeHandler.dateToStr(tmpBO.getStartTime(), "MM-dd HH:mm"));
			countingMap.put(i, tmpBO);
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

	private String getTimingKey(CryptoCoinBigMove po) {
		TimeUnitType timeUnitType = TimeUnitType.getType(po.getTimeUnitCode());
		if (timeUnitType == null) {
			return null;
		}
		return po.getTimeRange() + "_" + timeUnitType.getName();
	}

	private CryptoCoinBigMoveDataBO poToBo(CryptoCoinBigMove po) {
		CryptoCoinBigMoveDataBO bo = new CryptoCoinBigMoveDataBO();
		bo.setBigMoveTimeStr(localDateTimeHandler.dateToStr(po.getEventTime()));
		bo.setRate(po.getRate());
		bo.setSymbol(po.getSymbol());
		bo.setTimeRange(po.getTimeRange());
		bo.setTimeUnitTypeCode(po.getTimeUnitCode());
		if (po.getRate().compareTo(BigDecimal.ZERO) > 0) {
			bo.setRedirect("🟢");
		} else {
			bo.setRedirect("🔴");
		}
		return bo;
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
}
