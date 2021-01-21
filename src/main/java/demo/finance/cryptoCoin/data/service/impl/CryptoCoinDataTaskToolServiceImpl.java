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

	@Scheduled(cron = "0 */5 * * * ?")
	public void summaryHistoryData() {
		cryptoCoin5MinuteDataSummaryService.summaryHistoryData();
		cryptoCoin60MinuteDataSummaryService.summaryHistoryData();
		cryptoCoin1DayDataSummaryService.summaryHistoryData();
		cryptoCoin1WeekDataSummaryService.summaryHistoryData();
		cryptoCoin1MonthDataSummaryService.summaryHistoryData();
	}

	@Scheduled(cron = "* 2 */1 * * ?")
	public void deleteExpiredCacheData() {
		cryptoCoin1MinuteDataSummaryService.deleteExpiredCacheData();
		cryptoCoin5MinuteDataSummaryService.deleteExpiredCacheData();
		cryptoCoin60MinuteDataSummaryService.deleteExpiredCacheData();
	}
	
}
