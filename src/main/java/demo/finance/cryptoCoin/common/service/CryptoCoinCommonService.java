package demo.finance.cryptoCoin.common.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.common.service.impl.FinanceCommonService;
import demo.finance.cryptoCoin.data.pojo.result.FilterBODataResult;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;
import finance.cryptoCoin.pojo.type.CryptoCoinType;

public abstract class CryptoCoinCommonService extends FinanceCommonService {

	protected FilterBODataResult filterData(List<CryptoCoinPriceCommonDataBO> list) {
		FilterBODataResult r = new FilterBODataResult();

		if (list == null || list.isEmpty()) {
			r.setMessage("empty history data");
			return r;
		}

		CryptoCoinPriceCommonDataBO defaultData = list.get(0);
		BigDecimal maxPrice = defaultData.getHighPrice();
		BigDecimal minPrice = defaultData.getLowPrice();
		LocalDateTime maxPriceDateTime = defaultData.getCreateTime();
		LocalDateTime minPriceDateTime = defaultData.getCreateTime();
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		for (CryptoCoinPriceCommonDataBO po : list) {
			if (maxPrice.compareTo(po.getHighPrice()) < 0) {
				maxPrice = po.getHighPrice();
				maxPriceDateTime = po.getStartTime();
			} else if (minPrice.compareTo(po.getLowPrice()) > 0) {
				minPrice = po.getLowPrice();
				minPriceDateTime = po.getStartTime();
			}

			if (startTime == null || startTime.isAfter(po.getStartTime())) {
				startTime = po.getStartTime();
			}
			if (endTime == null || endTime.isBefore(po.getEndTime())) {
				endTime = po.getEndTime();
			}

		}

		r.setMaxPrice(maxPrice);
		r.setMinPrice(minPrice);
		r.setMaxPriceDateTime(maxPriceDateTime);
		r.setMinPriceDateTime(minPriceDateTime);
		r.setIsSuccess();
		return r;
	}

	protected CryptoCoinPriceCommonDataBO mergerData(CryptoCoinPriceCommonDataBO o, CryptoCoinPriceCommonDataBO t) {
		if (o == null || t == null) {
			return o;
		}

		try {
			if (o.getStartTime().isAfter(t.getStartTime())) {
				o.setStartTime(t.getStartTime());
				if (t.getStartPrice() != null) {
					o.setStartPrice(t.getStartPrice());
				}
			}
		} catch (Exception e) {
		}

		try {
			if (o.getEndTime().isBefore(t.getEndTime())) {
				o.setEndTime(t.getEndTime());
				if (t.getEndPrice() != null) {
					o.setEndPrice(t.getEndPrice());
				}
			}
		} catch (Exception e) {
		}

		if (t.getHighPrice() != null) {
			if (o.getHighPrice() == null) {
				o.setHighPrice(t.getHighPrice());
			} else if (o.getHighPrice() != null && o.getHighPrice().doubleValue() < t.getHighPrice().doubleValue()) {
				o.setHighPrice(t.getHighPrice());
			}
		}

		if (t.getLowPrice() != null) {
			if (o.getLowPrice() == null) {
				o.setLowPrice(t.getLowPrice());
			} else if (o.getLowPrice() != null && o.getLowPrice().doubleValue() > t.getLowPrice().doubleValue()) {
				o.setLowPrice(t.getLowPrice());
			}
		}

		if (t.getVolume() != null) {
			if (o.getVolume() == null) {
				o.setVolume(t.getVolume());
			} else {
				o.setVolume(o.getVolume().add(t.getVolume()));
			}
		}

		return o;
	}

	/**
	 * for test use
	 */
	protected List<CryptoCoinPriceCommonDataBO> buildFakeData(CryptoCoinType coinType, CurrencyType currencyType,
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
			bo.setCoinType(coinType.getCode());
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

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
			}
			
			resultDataList.add(tmpPOData);
			
			poDataExists = false;
			startTime = nextStepStartTimeByMinute(startTime, minuteStepLong);
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

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
			}
			
			resultDataList.add(tmpPOData);
			
			poDataExists = false;
			startTime = startTime.plusHours(1);
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

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
			}
			
			resultDataList.add(tmpPOData);
			
			poDataExists = false;
			startTime = startTime.plusDays(1);
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

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
			}
			
			resultDataList.add(tmpPOData);
			
			poDataExists = false;
			startTime = startTime.plusDays(7);
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

			} else {
				tmpPOData = new CryptoCoinPriceCommonDataBO();
				tmpCacheData = cacheDataList.get(0);
				tmpPOData.setCoinType(tmpCacheData.getCoinType());
				tmpPOData.setCurrencyType(tmpCacheData.getCurrencyType());
				tmpPOData.setVolume(BigDecimal.ZERO);

				for (int i = 0; i < cacheDataList.size(); i++) {
					tmpCacheData = cacheDataList.get(i);
					if (!tmpCacheData.getStartTime().isBefore(stepStart) && !tmpCacheData.getStartTime().isAfter(nextStepTime)) {
						tmpPOData = mergerData(tmpPOData, tmpCacheData);
					}
				}
			}
			
			resultDataList.add(tmpPOData);
			
			poDataExists = false;
			startTime = startTime.plusMonths(1);
			nextStepTime = nextStepTime.plusMonths(1);
			
		}
		
		Collections.sort(resultDataList);
		return resultDataList;
	}
}
