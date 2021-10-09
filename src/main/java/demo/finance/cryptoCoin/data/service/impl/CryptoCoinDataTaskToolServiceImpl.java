package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin1WeekDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.data.service.CryptoCoin60MinuteDataSummaryService;
import demo.finance.cryptoCoin.tool.service.CryptoCoinLowPriceNoticeService;

@Component
public class CryptoCoinDataTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CryptoCoin1MinuteDataSummaryService cryptoCoin1MinuteDataSummaryService;
	@Autowired
	private CryptoCoin5MinuteDataSummaryService cryptoCoin5MinuteDataSummaryService;
	@Autowired
	private CryptoCoin60MinuteDataSummaryService cryptoCoin60MinuteDataSummaryService;
	@Autowired
	private CryptoCoin1DayDataSummaryService cryptoCoin1DayDataSummaryService;
	@Autowired
	private CryptoCoin1WeekDataSummaryService cryptoCoin1WeekDataSummaryService;
	@Autowired
	private CryptoCoin1MonthDataSummaryService cryptoCoin1MonthDataSummaryService;
	@Autowired
	private CryptoCoinLowPriceNoticeService cryptoCoinLowPriceNoticeService;

	@Scheduled(cron = "0 */2 * * * ?")
	public void summaryMinuteData() {
		cryptoCoin1MinuteDataSummaryService.summaryLowPriceRedisData();
	}
	
	@Scheduled(cron="20 19 03 * * *")
	public void summaryHistoryData() {
		cryptoCoin5MinuteDataSummaryService.summaryHistoryData();
		cryptoCoin60MinuteDataSummaryService.summaryHistoryData();
		cryptoCoin1WeekDataSummaryService.summaryHistoryData();
		cryptoCoin1MonthDataSummaryService.summaryHistoryData();
	}

	@Scheduled(cron = "* 2 */1 * * ?")
	public void deleteExpiredCacheData() {
		cryptoCoin1MinuteDataSummaryService.deleteExpiredCacheData();
		cryptoCoin5MinuteDataSummaryService.deleteExpiredCacheData();
		cryptoCoin60MinuteDataSummaryService.deleteExpiredCacheData();
	}

	@Scheduled(cron="20 19 01 * * *")
	@Scheduled(cron="20 19 03 * * *")
	@Scheduled(cron="20 19 05 * * *")
	public void sendCryptoCoinDailyDataQueryMsg() {
		cryptoCoin1DayDataSummaryService.sendAllCryptoCoinDailyDataQueryMsg();
	}
	
	@Scheduled(cron="00 00 00 * * *")
	public void resetDailyDataWaitingQuerySet() {
		cryptoCoin1DayDataSummaryService.resetDailyDataWaitingQuerySet();
	}
	
	@Scheduled(cron="56 45 7 * * *")
	@Scheduled(cron="56 45 15 * * *")
	@Scheduled(cron="56 45 23 * * *")
	public void setNewLowPriceSubscription() {
		cryptoCoinLowPriceNoticeService.setNewLowPriceSubscription();
	}
	
	@Scheduled(cron="56 05 8 * * *")
	@Scheduled(cron="56 05 16 * * *")
	@Scheduled(cron="56 05 0 * * *")
	public void setLowPriceCoinWatching() {
		cryptoCoinLowPriceNoticeService.setLowPriceCoinWatching();
	}
}
