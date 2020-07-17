package demo.finance.metal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.metal.service.PreciousMetal5MinuteDataSummaryService;
import demo.finance.metal.service.PreciousMetalService;

@Component
public class FinanceTaskToolServiceImpl extends PreciousMetalCommonService {

	@Autowired
	private PreciousMetalService preciousMetalService;
	@Autowired
	private PreciousMetal5MinuteDataSummaryService dataSummary5MinuteService;

	@Scheduled(cron = "0 */1 * * * ?")
	public void preciousMetalPriceNoticeHandler() {
		preciousMetalService.noticeHandler();
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void cacheDataTo5Minute() {
		if(isPreciousMetalsTransactionTime()) {
			dataSummary5MinuteService.cacheDataTo5Minute();
		}
	}

	@Scheduled(cron = "* 2 */1 * * ?")
	public void deleteExpiredCacheData() {
		preciousMetalService.deleteExpiredCacheData();
	}
}
