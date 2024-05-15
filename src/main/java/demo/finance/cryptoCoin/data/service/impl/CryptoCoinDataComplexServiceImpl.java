package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
import demo.common.service.CommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinBigMoveMapper;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinBigMoveDailySummaryBO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMove;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinBigMoveExample;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveDataBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveSummaryDataBO;

@Service
public class CryptoCoinDataComplexServiceImpl extends CommonService implements CryptoCoinDataComplexService {

	@Autowired
	private CryptoCoinBigMoveMapper cryptoCoinBigMoveMapper;

	@Override
	public void getNewBigMoveDataMessage(String msg) {
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
	public ModelAndView getBigMoveSummaryByManual() {
		ModelAndView v = new ModelAndView("cryptoCoin/getBigMoveSummaryByManual");
		v.addObject("title", "getBigMoveSummaryByManual");

		CryptoCoinBigMoveExample example = new CryptoCoinBigMoveExample();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneMonthAgo = now.minusMonths(1);
		example.createCriteria().andEventTimeBetween(oneMonthAgo, now);
		List<CryptoCoinBigMove> bigMoveDataList = cryptoCoinBigMoveMapper.selectByExample(example);

		v = handleRecentBigMoveDataSummary(v, bigMoveDataList);
		v = handle24hBigMoveDataSummary(v, bigMoveDataList);
		v = handle24To48hBigMoveDataSummary(v, bigMoveDataList);
		v = handleLastWeekBigMoveDataSummary(v, bigMoveDataList);
		v = handleLastMonthBigMoveDataChart(v, bigMoveDataList);

		return v;
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

	private List<CryptoCoinBigMoveSummaryDataBO> buildSummaryListByStartTimeRange(
			List<CryptoCoinBigMove> bigMoveDataList, LocalDateTime startTime) {
		LocalDateTime endTime = LocalDateTime.now();
		return buildSummaryListByStartTimeRange(bigMoveDataList, startTime, endTime);
	}

	private List<CryptoCoinBigMoveSummaryDataBO> buildSummaryListByStartTimeRange(
			List<CryptoCoinBigMove> bigMoveDataList, LocalDateTime startTime, LocalDateTime endTime) {
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
			return summaryList;
		}

		summaryList.addAll(summaryMap.values());
		Collections.sort(summaryList);
		return summaryList;
	}

	private ModelAndView handle24hBigMoveDataSummary(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList) {
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		List<CryptoCoinBigMoveSummaryDataBO> bigMoveList = buildSummaryListByStartTimeRange(bigMoveDataList, yesterday);

		if (bigMoveList.isEmpty()) {
			v.addObject("todayBigMoveMsg", "Can NOT found any 24h big move");
			return v;
		}
		v.addObject("bigMoveIn24H", bigMoveList);
		return v;
	}

	private ModelAndView handle24To48hBigMoveDataSummary(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList) {
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		LocalDateTime start = LocalDateTime.now().minusDays(2);
		LocalDateTime end = LocalDateTime.now().minusDays(1);
		List<CryptoCoinBigMoveSummaryDataBO> bigMoveList = buildSummaryListByStartTimeRange(bigMoveDataList, start,
				end);

		if (bigMoveList.isEmpty()) {
			v.addObject("yesterdayBigMoveMsg", "Can NOT found any 24-48h big move");
			return v;
		}
		v.addObject("bigMoveIn48H", bigMoveList);
		return v;
	}

	private ModelAndView handleLastWeekBigMoveDataSummary(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList) {
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		LocalDateTime lastWeek = LocalDateTime.now().minusDays(7);
		List<CryptoCoinBigMoveSummaryDataBO> bigMoveList = buildSummaryListByStartTimeRange(bigMoveDataList, lastWeek);

		if (bigMoveList.isEmpty()) {
			v.addObject("lastWeekBigMoveMsg", "Can NOT found any last week big move");
			return v;
		}
		v.addObject("bigMoveInLastWeek", bigMoveList);
		return v;
	}

	private ModelAndView handleLastMonthBigMoveDataChart(ModelAndView v, List<CryptoCoinBigMove> bigMoveDataList) {
		if (bigMoveDataList == null || bigMoveDataList.isEmpty()) {
			return v;
		}
		LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
		LocalDateTime start = todayStart.minusMonths(1);
		LocalDateTime tmpDateTime = start;
		Map<LocalDateTime, CryptoCoinBigMoveDailySummaryBO> countingMap = new HashMap<>();
		CryptoCoinBigMoveDailySummaryBO tmpBO = null;
		for (int i = 0; i < bigMoveDataList.size(); i++) {
			CryptoCoinBigMove data = bigMoveDataList.get(i);
			LocalDateTime startTime = data.getEventTime().with(LocalTime.MIN);
			if (countingMap.containsKey(startTime)) {
				tmpBO = countingMap.get(startTime);
				tmpBO.setTotal(tmpBO.getTotal() + 1);
				if (data.getSymbol().contains("_")) {
					tmpBO.setGateIoCounting(tmpBO.getGateIoCounting() + 1);
				} else {
					tmpBO.setBinanceCounting(tmpBO.getBinanceCounting() + 1);
				}
			} else {
				tmpBO = new CryptoCoinBigMoveDailySummaryBO();
				tmpBO.setDatetime(tmpDateTime);
				tmpBO.setTotal(tmpBO.getTotal() + 1);
				countingMap.put(startTime, tmpBO);
			}
		}
		List<CryptoCoinBigMoveDailySummaryBO> resultList = new ArrayList<>();
		resultList.addAll(countingMap.values());
		Collections.sort(resultList);

		v.addObject("bigMoveDailySummaryData", resultList);

		return v;
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
}
