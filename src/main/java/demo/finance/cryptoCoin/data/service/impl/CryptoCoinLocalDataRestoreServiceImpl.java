package demo.finance.cryptoCoin.data.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1dayMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1monthMapper;
import demo.finance.cryptoCoin.data.mapper.CryptoCoinPrice1weekMapper;
import demo.finance.cryptoCoin.data.pojo.dto.FindFirstReasonableDayDTO;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1day;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1dayExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1month;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1monthExample;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1week;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinPrice1weekExample;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinCatalogService;
import demo.finance.cryptoCoin.data.service.CryptoCoinLocalDataRestoreService;
import demo.test.mapper.TestMapper;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoCoinLocalDataRestoreServiceImpl extends CryptoCoinCommonService
		implements CryptoCoinLocalDataRestoreService {

	@Autowired
	private CryptoCoinCatalogService coinCatalogService;

	@Autowired
	private TestMapper testMapper;
	@Autowired
	private CryptoCoinPrice1dayMapper dailyMapper;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataService;

	@Autowired
	private CryptoCoinPrice1weekMapper weeklyMapper;
	@Autowired
	private CryptoCoinPrice1monthMapper monthlyMapper;

	@Override
	public void restoreWeekData() {
		LocalDateTime firstMondayStart = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat("2015-09-28 00:00:00");
		LocalDateTime now = LocalDateTime.now();

		CurrencyType currencyType = CurrencyType.USD;

		LocalDateTime tmpMondayStart = firstMondayStart;
		LocalDateTime tmpFirstStart = null;

		List<CryptoCoinCatalog> coinCatalogList = coinCatalogService.getAllCatalog();
		String lastEnd = "ARPA";
		boolean startFlag = false;

		for (CryptoCoinCatalog coinType : coinCatalogList) {
			System.out.println("Handle: " + coinType.getCoinNameEnShort());
			if (!startFlag) {
				startFlag = coinType.getCoinNameEnShort().equals(lastEnd);
				System.out.println("Skip: " + coinType.getCoinNameEnShort());
				continue;
			}
			tmpFirstStart = findFirstMondayOfCoinType(coinType, tmpMondayStart);
			if (tmpFirstStart == null) {
				System.out.println("Can NOT find reasonable start date, skip.");
				continue;
			}
			tmpMondayStart = tmpFirstStart;
			while (tmpMondayStart.isBefore(now)) {
				System.out.println("handle: " + tmpMondayStart + ", coinType: " + coinType.getCoinNameEnShort()
						+ ", currencyType: " + currencyType);
				handleHistoryDataListForWeekly(tmpMondayStart, coinType, currencyType);
				tmpMondayStart = tmpMondayStart.plusDays(7);
			}
			tmpMondayStart = firstMondayStart;
		}

		System.out.println("end");
	}

	private void handleHistoryDataListForWeekly(LocalDateTime startTime, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {

		LocalDateTime endTime = startTime.plusDays(7).with(LocalTime.MAX);

		List<CryptoCoinPriceCommonDataBO> cacheDataList = dailyDataService.getCommonDataList(coinType, currencyType,
				startTime, endTime);
		if (cacheDataList == null || cacheDataList.isEmpty()) {
			return;
		}

		CryptoCoinPrice1weekExample example = new CryptoCoinPrice1weekExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice1week> poList = weeklyMapper.selectByExample(example);
		CryptoCoinPrice1week po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice1week();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getId());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPriceCommonDataBO cache : cacheDataList) {
			if (po.getStartTime() == null || cache.getStartTime().isBefore(po.getStartTime())
					|| cache.getStartTime().isEqual(po.getStartTime())) {
				po.setStartTime(cache.getStartTime());
				po.setStartPrice(cache.getStartPrice());
			}
			if (po.getEndTime() == null || cache.getEndTime().isAfter(po.getEndTime())
					|| cache.getEndTime().isEqual(po.getEndTime())) {
				po.setEndTime(cache.getEndTime());
				po.setEndPrice(cache.getEndPrice());
			}
			if (po.getHighPrice() == null || po.getHighPrice().compareTo(cache.getHighPrice()) < 0) {
				po.setHighPrice(cache.getHighPrice());
			}
			if (po.getLowPrice() == null || po.getLowPrice().compareTo(cache.getLowPrice()) > 0) {
				po.setLowPrice(cache.getLowPrice());
			}
			volumeSummary += cache.getVolume().doubleValue();
		}
		po.setVolume(new BigDecimal(volumeSummary));

		if (newPOFlag) {
			weeklyMapper.insertSelective(po);
		} else {
			weeklyMapper.updateByPrimaryKeySelective(po);
		}
	}

	private LocalDateTime findFirstMondayOfCoinType(CryptoCoinCatalog coinType, LocalDateTime startTime) {
		FindFirstReasonableDayDTO dto = null;
		List<CryptoCoinPrice1day> dailyDataList = new ArrayList<>();
		CryptoCoinPrice1day tmpData = null;
		boolean reasonableDataFlag = false;
		boolean endFlag = false;
		LocalDateTime indexTime = null;

		while (!reasonableDataFlag && !endFlag) {
			dto = new FindFirstReasonableDayDTO();
			dto.setCoinTypeId(coinType.getId());
			dto.setStartTime(indexTime);
			dailyDataList = testMapper.findCryptoCoinStartTime(dto);
			if (dailyDataList.size() <= 1) {
				endFlag = true;
				continue;
			}

			for (int i = 0; i < dailyDataList.size() && !reasonableDataFlag; i++) {
				tmpData = dailyDataList.get(i);
				reasonableDataFlag = (tmpData.getStartPrice().doubleValue() != 0
						|| tmpData.getEndPrice().doubleValue() != 0 || tmpData.getHighPrice().doubleValue() != 0
						|| tmpData.getLowPrice().doubleValue() != 0);
				indexTime = tmpData.getStartTime();
			}
		}

		if (reasonableDataFlag) {
			LocalDateTime firstDay = tmpData.getStartTime();
			LocalDateTime tmpDay = firstDay;
			while (tmpDay.getDayOfWeek().getValue() != 1) {
				tmpDay = tmpDay.minusDays(1);
			}
			return tmpDay;
		} else {
			return null;
		}
	}

	private LocalDateTime findFirstMonthStartOfCoinType(CryptoCoinCatalog coinType, LocalDateTime startTime) {
		FindFirstReasonableDayDTO dto = null;
		List<CryptoCoinPrice1day> dailyDataList = new ArrayList<>();
		CryptoCoinPrice1day tmpData = null;
		boolean reasonableDataFlag = false;
		boolean endFlag = false;
		LocalDateTime indexTime = null;

		while (!reasonableDataFlag && !endFlag) {
			dto = new FindFirstReasonableDayDTO();
			dto.setCoinTypeId(coinType.getId());
			dto.setStartTime(indexTime);
			dailyDataList = testMapper.findCryptoCoinStartTime(dto);
			if (dailyDataList.size() <= 1) {
				endFlag = true;
				continue;
			}

			for (int i = 0; i < dailyDataList.size() && !reasonableDataFlag; i++) {
				tmpData = dailyDataList.get(i);
				reasonableDataFlag = (tmpData.getStartPrice().doubleValue() != 0
						|| tmpData.getEndPrice().doubleValue() != 0 || tmpData.getHighPrice().doubleValue() != 0
						|| tmpData.getLowPrice().doubleValue() != 0);
				indexTime = tmpData.getStartTime();
			}
		}

		if (reasonableDataFlag) {
			LocalDateTime tmpDay = tmpData.getStartTime();
			tmpDay = tmpDay.withDayOfMonth(1).with(LocalTime.MIN);
			return tmpDay;
		} else {
			return null;
		}
	}

	@Override
	public void restoreMonthData() {
		LocalDateTime firstMonthStart = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat("2015-09-01 00:00:00");
		LocalDateTime now = LocalDateTime.now();
		CurrencyType currencyType = CurrencyType.USD;
		LocalDateTime tmpMonthStart = firstMonthStart;
		LocalDateTime tmpFirstStart = null;

		List<CryptoCoinCatalog> coinCatalogList = coinCatalogService.getAllCatalog();
		String lastEnd = "TEST";
		boolean startFlag = false;

		for (CryptoCoinCatalog coinType : coinCatalogList) {
			System.out.println("Handle: " + coinType.getCoinNameEnShort());
			if (!startFlag) {
				startFlag = coinType.getCoinNameEnShort().equals(lastEnd);
				System.out.println("Skip: " + coinType.getCoinNameEnShort());
				continue;
			}
			tmpFirstStart = findFirstMonthStartOfCoinType(coinType, tmpMonthStart);
			if (tmpFirstStart == null) {
				System.out.println("Can NOT find reasonable start date, skip.");
				continue;
			}
			tmpMonthStart = tmpFirstStart;
			while (tmpMonthStart.isBefore(now)) {
				System.out.println("handle: " + tmpMonthStart + ", coinType: " + coinType.getCoinNameEnShort()
						+ ", currencyType: " + currencyType);
				handleHistoryDataListForMonthly(tmpMonthStart, coinType, currencyType);
				tmpMonthStart = tmpMonthStart.plusMonths(1);
			}
			tmpMonthStart = firstMonthStart;
		}

		System.out.println("end");
	}

	private void handleHistoryDataListForMonthly(LocalDateTime startTime, CryptoCoinCatalog coinType,
			CurrencyType currencyType) {
		LocalDateTime endTime = startTime.plusMonths(1).with(LocalTime.MAX);
		List<CryptoCoinPriceCommonDataBO> cacheDataList = dailyDataService.getCommonDataList(coinType, currencyType,
				startTime, endTime);
		if (cacheDataList == null || cacheDataList.isEmpty()) {
			return;
		}

		CryptoCoinPrice1monthExample example = new CryptoCoinPrice1monthExample();
		example.createCriteria().andCoinTypeEqualTo(coinType.getId()).andCurrencyTypeEqualTo(currencyType.getCode())
				.andStartTimeEqualTo(startTime);
		List<CryptoCoinPrice1month> poList = monthlyMapper.selectByExample(example);
		CryptoCoinPrice1month po = null;
		boolean newPOFlag = false;
		if (poList == null || poList.isEmpty()) {
			newPOFlag = true;
			po = new CryptoCoinPrice1month();
			po.setId(snowFlake.getNextId());
			po.setCoinType(coinType.getId());
			po.setCurrencyType(currencyType.getCode());
		} else {
			po = poList.get(0);
		}

		Double volumeSummary = 0D;
		for (CryptoCoinPriceCommonDataBO cache : cacheDataList) {
			if (po.getStartTime() == null || cache.getStartTime().isBefore(po.getStartTime())
					|| cache.getStartTime().isEqual(po.getStartTime())) {
				po.setStartTime(cache.getStartTime());
				po.setStartPrice(cache.getStartPrice());
			}
			if (po.getEndTime() == null || cache.getEndTime().isAfter(po.getEndTime())
					|| cache.getEndTime().isEqual(po.getEndTime())) {
				po.setEndTime(cache.getEndTime());
				po.setEndPrice(cache.getEndPrice());
			}
			if (po.getHighPrice() == null || po.getHighPrice().compareTo(cache.getHighPrice()) < 0) {
				po.setHighPrice(cache.getHighPrice());
			}
			if (po.getLowPrice() == null || po.getLowPrice().compareTo(cache.getLowPrice()) > 0) {
				po.setLowPrice(cache.getLowPrice());
			}
			volumeSummary += cache.getVolume().doubleValue();
		}
		po.setVolume(new BigDecimal(volumeSummary));

		if (newPOFlag) {
			monthlyMapper.insertSelective(po);
		} else {
			monthlyMapper.updateByPrimaryKeySelective(po);
		}
	}

	@Override
	public Long cleanDuplicateDailyData() {
		Long deleteCount = 0L;
		List<Long> idList = new ArrayList<>();
		List<HashMap<Object, Object>> mapList = testMapper.findDuplicateDailyData();

		Long tmpId = null;

		while (mapList != null && !mapList.isEmpty()) {
			deleteCount += mapList.size();
			for (HashMap<Object, Object> subMap : mapList) {
				try {
					tmpId = Long.parseLong(String.valueOf(subMap.get("id")));
					idList.add(tmpId);
				} catch (Exception e) {
				}
			}

			CryptoCoinPrice1dayExample dailyDataExample = new CryptoCoinPrice1dayExample();
			dailyDataExample.createCriteria().andIdIn(idList);
			dailyMapper.deleteByExample(dailyDataExample);

			mapList = testMapper.findDuplicateDailyData();
		}

		return deleteCount;
	}

	@Override
	public Long cleanDuplicateDailyDataInTheSameDay() {
		Long deleteCount = 0L;
		List<HashMap<Object, Object>> mapList = testMapper.findDuplicateDailyDataInTheSameDay();
		Long tmpCoinType = null;
		String tmpDateString = null;
		LocalDateTime tmpDate = null;
		List<CryptoCoinPrice1day> dailyDataList = null;
		
		List<Long> deleteIdList = new ArrayList<>();

		while (mapList != null && !mapList.isEmpty()) {
			for (HashMap<Object, Object> subMap : mapList) {
				try {
					tmpCoinType = Long.parseLong(String.valueOf(subMap.get("coin_type")));
					tmpDateString = String.valueOf(subMap.get("start_time"));
					tmpDate = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(tmpDateString);
				} catch (Exception e) {
				}
				
				CryptoCoinPrice1dayExample dailyDataExample = new CryptoCoinPrice1dayExample();
				dailyDataExample.createCriteria().andCoinTypeEqualTo(tmpCoinType)
				.andStartTimeBetween(tmpDate.with(LocalTime.MIN), tmpDate.withHour(23).withMinute(59).withSecond(59));
				dailyDataList = dailyMapper.selectByExample(dailyDataExample);
				
				for (CryptoCoinPrice1day dailyData : dailyDataList) {
					if (!tmpDate.with(LocalTime.MIN).equals(dailyData.getStartTime())) {
						deleteIdList.add(dailyData.getId());
						System.out.println("Add: " + dailyData.getId() + ", coinType: " + dailyData.getCoinType() + ", " + dailyData.getStartTime());
					}
				}

				if(deleteIdList.size() > 1000) {
					System.out.println("Delete");
					CryptoCoinPrice1dayExample dailyDataDeleteExample = new CryptoCoinPrice1dayExample();
					dailyDataDeleteExample.createCriteria().andIdIn(deleteIdList);
					deleteCount += dailyMapper.deleteByExample(dailyDataDeleteExample);
					deleteIdList.clear();
				}
			}
			

			mapList = testMapper.findDuplicateDailyDataInTheSameDay();
			
			if(!deleteIdList.isEmpty()) {
				System.out.println("Delete");
				CryptoCoinPrice1dayExample dailyDataDeleteExample = new CryptoCoinPrice1dayExample();
				dailyDataDeleteExample.createCriteria().andIdIn(deleteIdList);
				deleteCount += dailyMapper.deleteByExample(dailyDataDeleteExample);
				deleteIdList.clear();
			}
		}
		

		System.out.println("End");
		return deleteCount;
	}
	
}
