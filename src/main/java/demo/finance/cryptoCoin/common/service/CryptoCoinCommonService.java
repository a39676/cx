package demo.finance.cryptoCoin.common.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1WeekDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;

public abstract class CryptoCoinCommonService extends FinanceCommonService {

	@Autowired
	protected CryptoCoinPriceCacheService cacheService;
	@Autowired
	protected CryptoCoin1MinuteDataSummaryService _1MinDataService;
	@Autowired
	protected CryptoCoin5MinuteDataSummaryService _5MinDataService;
	@Autowired
	protected CryptoCoin60MinuteDataSummaryService hourDataService;
	@Autowired
	protected CryptoCoin1DayDataSummaryService dailyDataService;
	@Autowired
	protected CryptoCoin1WeekDataSummaryService weeklyDataService;
	@Autowired
	protected CryptoCoin1MonthDataSummaryService monthlyDataService;
	
	@Autowired
	protected CryptoCoinCatalogService coinCatalogService;
	
	protected FilterBODataResult filterData(List<CryptoCoinPriceCommonDataBO> list) {
		FilterBODataResult r = new FilterBODataResult();

		if (list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}

		double maxPrice = Double.MIN_VALUE;
		double minPrice = Double.MAX_VALUE;
		LocalDateTime maxPriceDateTime = null;
		LocalDateTime minPriceDateTime = null;
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		for (CryptoCoinPriceCommonDataBO bo : list) {
			if(bo.getHighPrice() != null && bo.getHighPrice().doubleValue() > maxPrice) {
				maxPrice = bo.getHighPrice().doubleValue();
				maxPriceDateTime = bo.getStartTime();
			}
			
			if(bo.getLowPrice() != null && bo.getLowPrice().doubleValue() < minPrice) {
				minPrice = bo.getLowPrice().doubleValue();
				minPriceDateTime = bo.getStartTime();
			}

			if(bo.getStartTime() != null) {
				if (startTime == null || startTime.isAfter(bo.getStartTime())) {
					startTime = bo.getStartTime();
				}
			}
			if(bo.getEndTime() != null) {
				if (endTime == null || endTime.isBefore(bo.getEndTime())) {
					endTime = bo.getEndTime();
				}
			}

		}

		r.setMaxPrice(new BigDecimal(maxPrice));
		r.setMinPrice(new BigDecimal(minPrice));
		r.setMaxPriceDateTime(maxPriceDateTime);
		r.setMinPriceDateTime(minPriceDateTime);
		r.setIsSuccess();
		return r;
	}

	protected CryptoCoinPriceCommonDataBO mergerData(CryptoCoinPriceCommonDataBO resultTarget, CryptoCoinPriceCommonDataBO otherData) {
		if (resultTarget == null || otherData == null) {
			return resultTarget;
		}

		try {
			if (resultTarget.getStartTime().isAfter(otherData.getStartTime())) {
				resultTarget.setStartTime(otherData.getStartTime());
				if (otherData.getStartPrice() != null) {
					resultTarget.setStartPrice(otherData.getStartPrice());
				}
			}
		} catch (Exception e) {
		}
		
		/*
		 * resultTarget could be a new object
		 * means start time & end time could be null
		 */
		if(resultTarget.getStartTime() == null && otherData.getStartTime() != null) {
			resultTarget.setStartTime(otherData.getStartTime());
		}

		try {
			if (resultTarget.getEndTime().isBefore(otherData.getEndTime())) {
				resultTarget.setEndTime(otherData.getEndTime());
				if (otherData.getEndPrice() != null) {
					resultTarget.setEndPrice(otherData.getEndPrice());
				}
			}
		} catch (Exception e) {
		}

		if(resultTarget.getEndTime() == null && otherData.getEndTime() != null) {
			resultTarget.setEndTime(otherData.getEndTime());
		}
		
		if (otherData.getHighPrice() != null) {
			if (resultTarget.getHighPrice() == null) {
				resultTarget.setHighPrice(otherData.getHighPrice());
			} else if (resultTarget.getHighPrice() != null && resultTarget.getHighPrice().doubleValue() < otherData.getHighPrice().doubleValue()) {
				resultTarget.setHighPrice(otherData.getHighPrice());
			}
		}

		if (otherData.getLowPrice() != null) {
			if (resultTarget.getLowPrice() == null) {
				resultTarget.setLowPrice(otherData.getLowPrice());
			} else if (resultTarget.getLowPrice() != null && resultTarget.getLowPrice().doubleValue() > otherData.getLowPrice().doubleValue()) {
				resultTarget.setLowPrice(otherData.getLowPrice());
			}
		}

		if (otherData.getVolume() != null) {
			if (resultTarget.getVolume() == null) {
				resultTarget.setVolume(otherData.getVolume());
			} else {
				resultTarget.setVolume(resultTarget.getVolume().add(otherData.getVolume()));
			}
		}

		return resultTarget;
	}

	/**
	 * for test use
	 */
	protected List<CryptoCoinPriceCommonDataBO> buildFakeData(String coinType, CurrencyType currencyType,
			LocalDateTime startTime) {
		List<CryptoCoinPriceCommonDataBO> l = new ArrayList<>();

		CryptoCoinPriceCommonDataBO bo = null;
		startTime = startTime.withSecond(0).withNano(0);
		while (!startTime.isAfter(LocalDateTime.now())) {
			bo = new CryptoCoinPriceCommonDataBO();
			bo.setId(snowFlake.getNextId());
			bo.setStartTime(startTime);
			bo.setEndTime(startTime.plusMinutes(1));
			bo.setStartPrice(new BigDecimal("-99999"));
			bo.setEndPrice(new BigDecimal("-99999"));
			bo.setHighPrice(new BigDecimal("-99999"));
			bo.setLowPrice(new BigDecimal("-99999"));
			bo.setVolume(new BigDecimal("99999"));
			bo.setCoinType(coinType);
			bo.setCurrencyType(currencyType.getCode());

			l.add(bo);
			startTime = startTime.plusMinutes(1);
		}

		return l;
	}
	
	/**
	 * 为各个 data summary service 提供 PO data & cache data 的通用合并逻辑
	 * @param poDataList
	 * @param cacheDataList
	 * @param startTime
	 * @param timeRange
	 * @param timeUnitType
	 * @return
	 */
	protected List<CryptoCoinPriceCommonDataBO> mergePODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, Integer timeRange,
			TimeUnitType timeUnitType) {
		if (cacheDataList.isEmpty()) {
			return poDataList;
		}
		
		Collections.sort(cacheDataList);

		LocalDateTime endTime = null;
		if (TimeUnitType.minute.equals(timeUnitType)) {
			endTime = nextStepStartTimeByMinute(LocalDateTime.now(), timeRange);
			return mergeMinutePODataWithCache(poDataList, cacheDataList, startTime, endTime, timeRange);
		} else if (TimeUnitType.hour.equals(timeUnitType)) {
			endTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0).plusHours(1);
			return mergeHourPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		} else if (TimeUnitType.day.equals(timeUnitType)) {
			endTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).plusDays(1);
			return mergeDayPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		} else if (TimeUnitType.week.equals(timeUnitType)) {
			endTime = localDateTimeHandler.findNextDayOfWeek(LocalDateTime.now(), DayOfWeek.SUNDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
			return mergeWeekPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		} else if (TimeUnitType.month.equals(timeUnitType)) {
			endTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0).plusMonths(1);
			return mergeMonthPODataWithCache(poDataList, cacheDataList, startTime, endTime);
		}
		
		return poDataList;
	}
	
	private List<CryptoCoinPriceCommonDataBO> mergeMinutePODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime, Integer minuteStepLong) {
		
		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime;
		LocalDateTime nextStepTime = nextStepStartTimeByMinute(startTime, minuteStepLong);
		boolean poDataExists = false;
		
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while(!nextStepTime.isAfter(endTime)) {
			for(int poDataIndex = 0; poDataExists == false && poDataIndex < poDataList.size(); poDataIndex++) {
				tmpPOData = poDataList.get(poDataIndex);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart) && !tmpPOData.getStartTime().isAfter(nextStepTime));

			}
			
			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int cacheDataIndex = 0; cacheDataIndex < cacheDataList.size(); cacheDataIndex++) {
					tmpCacheData = cacheDataList.get(cacheDataIndex);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
				resultDataList.add(tmpPOData);
				
			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);
				
				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}
				
				if(hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}
			
			poDataExists = false;
			stepStart = nextStepStartTimeByMinute(stepStart, minuteStepLong);
			nextStepTime = nextStepStartTimeByMinute(nextStepTime, minuteStepLong);
			
		}
		
		Collections.sort(resultDataList);
		return resultDataList;
	}
	
	private List<CryptoCoinPriceCommonDataBO> mergeHourPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {
		
		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime.withMinute(0).withSecond(0).withNano(0);
		LocalDateTime nextStepTime = stepStart.plusHours(1);
		boolean poDataExists = false;
		
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while(!nextStepTime.isAfter(endTime)) {
			for(int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart) && !tmpPOData.getStartTime().isAfter(nextStepTime));
			}
			
			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
				resultDataList.add(tmpPOData);
				
			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}
				
				if(hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}
			
			poDataExists = false;
			stepStart = stepStart.plusHours(1);
			nextStepTime = nextStepTime.plusHours(1);
			
		}
		
		Collections.sort(resultDataList);
		return resultDataList;
	}
	
	private List<CryptoCoinPriceCommonDataBO> mergeDayPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {
		
		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime nextStepTime = stepStart.plusDays(1);
		boolean poDataExists = false;
		
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while(!nextStepTime.isAfter(endTime)) {
			for(int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart) && !tmpPOData.getStartTime().isAfter(nextStepTime));
			}
			
			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}

				resultDataList.add(tmpPOData);
				
			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}
				
				if(hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}
			
			
			
			poDataExists = false;
			stepStart = stepStart.plusDays(1);
			nextStepTime = nextStepTime.plusDays(1);
			
		}
		
		Collections.sort(resultDataList);
		return resultDataList;
	}
	
	private List<CryptoCoinPriceCommonDataBO> mergeWeekPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {
		
		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = localDateTimeHandler.findLastDayOfWeek(startTime, CryptoCoinDataConstant.START_DAY_OF_WEEK);
		LocalDateTime nextStepTime = stepStart.plusDays(7);
		boolean poDataExists = false;
		
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while(!nextStepTime.isAfter(endTime)) {
			for(int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart) && !tmpPOData.getStartTime().isAfter(nextStepTime));
			}
			
			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}

				resultDataList.add(tmpPOData);
				
			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}
				
				if(hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}
			
			
			poDataExists = false;
			stepStart = stepStart.plusDays(7);
			nextStepTime = nextStepTime.plusDays(7);
			
		}
		
		Collections.sort(resultDataList);
		return resultDataList;
	}
	
	private List<CryptoCoinPriceCommonDataBO> mergeMonthPODataWithCache(List<CryptoCoinPriceCommonDataBO> poDataList,
			List<CryptoCoinPriceCommonDataBO> cacheDataList, LocalDateTime startTime, LocalDateTime endTime) {
		
		List<CryptoCoinPriceCommonDataBO> resultDataList = new ArrayList<>();
		LocalDateTime stepStart = startTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime nextStepTime = stepStart.plusMonths(1);
		boolean poDataExists = false;
		
		CryptoCoinPriceCommonDataBO tmpPOData = null;
		CryptoCoinPriceCommonDataBO tmpCacheData = null;
		while(!nextStepTime.isAfter(endTime)) {
			for(int i = 0; poDataExists == false && i < poDataList.size(); i++) {
				tmpPOData = poDataList.get(i);
				poDataExists = (!tmpPOData.getStartTime().isBefore(stepStart) && !tmpPOData.getStartTime().isAfter(nextStepTime));
			}
			
			if (poDataExists) {
				// please sort(startTime early to later) cacheDataList before use
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}

				resultDataList.add(tmpPOData);
				
			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				boolean hasMathcCache = false;
				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
						hasMathcCache = true;
					}
				}
				
				if(hasMathcCache) {
					resultDataList.add(tmpPOData);
				}
			}
			
			
			poDataExists = false;
			stepStart = stepStart.plusMonths(1);
			nextStepTime = nextStepTime.plusMonths(1);
			
		}
		
		Collections.sort(resultDataList);
		return resultDataList;
	}

	protected List<CryptoCoinPriceCommonDataBO> getHistoryDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			TimeUnitType timeUnit, Integer timeRange) {
		LocalDateTime startTime = null;
		if (TimeUnitType.minute.equals(timeUnit)) {
			if (CryptoCoinDataConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				startTime = LocalDateTime.now().minusMinutes(timeRange).withSecond(0).withNano(0);
				return _1MinDataService.getCommonDataListFillWithCache(coinType, currencyType, startTime);
			} else if (CryptoCoinDataConstant.CRYPTO_COIN_5MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				startTime = nextStepStartTimeByMinute(LocalDateTime.now(), timeRange).minusMinutes(timeRange.longValue());
				return _5MinDataService.getCommonDataListFillWithCache(coinType, currencyType, startTime);
			}
		} else if (TimeUnitType.hour.equals(timeUnit)) {
			startTime = LocalDateTime.now().minusHours(timeRange).withMinute(0).withSecond(0).withNano(0);
			return hourDataService.getCommonDataList(coinType, currencyType, startTime);
		} else if (TimeUnitType.day.equals(timeUnit)) {
			startTime = LocalDateTime.now().minusDays(timeRange).withHour(0).withMinute(0).withSecond(0).withNano(0);
			return dailyDataService.getCommonDataList(coinType, currencyType, startTime);
		} else if (TimeUnitType.week.equals(timeUnit)) {
			LocalDateTime lastSunday = localDateTimeHandler.findLastDayOfWeek(LocalDateTime.now(), DayOfWeek.SUNDAY);
			startTime = lastSunday.minusDays((timeRange - 1) * 7).withSecond(0).withNano(0);
			return weeklyDataService.getCommonDataList(coinType, currencyType, startTime);
		} else if (TimeUnitType.month.equals(timeUnit)) {
			startTime = LocalDateTime.now().withDayOfMonth(1).minusMonths(timeRange - 1).withSecond(0).withNano(0);
			return monthlyDataService.getCommonDataList(coinType, currencyType, startTime);
		}

		return new ArrayList<CryptoCoinPriceCommonDataBO>();
	}

	protected BigDecimal numberSetScale(BigDecimal num) {
		if(num.compareTo(new BigDecimal(100)) >= 0) {
			return num.setScale(2, RoundingMode.HALF_UP);
		} else {
			return num.setScale(6, RoundingMode.HALF_UP);
		}
	}

}
