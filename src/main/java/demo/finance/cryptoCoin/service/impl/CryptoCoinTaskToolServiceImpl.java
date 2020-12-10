package demo.finance.cryptoCoin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.service.CryptoCoin1DayDataSummaryService;
import demo.finance.cryptoCoin.service.CryptoCoin1MinuteDataSummaryService;
import demo.finance.cryptoCoin.service.CryptoCoin1MonthDataSummaryService;
import demo.finance.cryptoCoin.service.CryptoCoin1WeekDataSummaryService;
import demo.finance.cryptoCoin.service.CryptoCoin5MinuteDataSummaryService;
import demo.finance.cryptoCoin.service.CryptoCoin60MinuteDataSummaryService;
import demo.finance.cryptoCoin.service.CryptoCoinNoticeService;
import demo.finance.cryptoCoin.service.CryptoCoinPriceService;

@Component
public class CryptoCoinTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CryptoCoinNoticeService noticeService;
	@Autowired
	private CryptoCoinPriceService priceService;
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
	

	@Scheduled(cron = "0 */1 * * * ?")
	public void cryptoCoinPriceNoticeHandler() {
		noticeService.noticeHandler();
	}
	
	@Scheduled(cron="0 */5 * * * ?")
	public void summaryHistoryData() {
		cryptoCoin5MinuteDataSummaryService.summaryHistoryData();
		cryptoCoin60MinuteDataSummaryService.summaryHistoryData();
		cryptoCoin1DayDataSummaryService.summaryHistoryData();
		cryptoCoin1WeekDataSummaryService.summaryHistoryData();
		cryptoCoin1MonthDataSummaryService.summaryHistoryData();
	}

	@Scheduled(cron = "* 2 */1 * * ?")
	public void deleteExpiredCacheData() {
		priceService.deleteExpiredCacheData();
	}
	
	@Scheduled(cron="40 50 23 * * *")
	public void deleteExpired1MinuteSummaryData() {
		cryptoCoin1MinuteDataSummaryService.deleteExpiredCacheData();
	}
	
	@Scheduled(cron="40 50 23 * * *")
	public void deleteExpired5MinuteSummaryData() {
		cryptoCoin5MinuteDataSummaryService.deleteExpiredCacheData();
	}
	
	@Scheduled(cron="40 55 23 * * *")
	public void deleteExpired60MinuteSummaryData() {
		cryptoCoin60MinuteDataSummaryService.deleteExpiredCacheData();
	}
	
	@Scheduled(cron="40 51 22 * * *")
	public void deleteOldNotice() {
		noticeService.deleteOldNotice();
	}
	
}
