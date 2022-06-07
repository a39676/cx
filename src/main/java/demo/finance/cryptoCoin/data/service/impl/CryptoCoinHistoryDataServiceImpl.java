package demo.finance.cryptoCoin.data.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;
import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinCatalog;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoinHistoryDataService;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.constant.CryptoCoinDataConstant;

@Service
public class CryptoCoinHistoryDataServiceImpl extends CryptoCoinCommonService implements CryptoCoinHistoryDataService {

	@Autowired
	private CryptoCoin1MinuteDataSummaryService minuteDataService;
	@Autowired
	private CryptoCoin5MinuteDataSummaryService _5MinDataService;
	@Autowired
	private CryptoCoin60MinuteDataSummaryService hourDataService;
	@Autowired
	private CryptoCoin1DayDataSummaryService dailyDataService;
	@Autowired
	private CryptoCoin1MonthDataSummaryService monthlyDataService;
	
	
	@Override
	public List<CryptoCoinPriceCommonDataBO> getHistoryDataList(CryptoCoinCatalog coinType, CurrencyType currencyType,
			TimeUnitType timeUnit, Integer timeRange) {
		LocalDateTime startTime = null;
		if (TimeUnitType.minute.equals(timeUnit)) {
			if (CryptoCoinDataConstant.CRYPTO_COIN_1MINUTE_DATA_LIVE_HOURS * 60 > timeRange) {
				startTime = LocalDateTime.now().minusMinutes(timeRange).withSecond(0).withNano(0);
				return minuteDataService.getCommonDataListFillWithCache(coinType, currencyType, startTime);
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
		} else if (TimeUnitType.month.equals(timeUnit)) {
			startTime = LocalDateTime.now().withDayOfMonth(1).minusMonths(timeRange - 1).withSecond(0).withNano(0);
			return monthlyDataService.getCommonDataList(coinType, currencyType, startTime);
		}

		return new ArrayList<CryptoCoinPriceCommonDataBO>();
	}
}
