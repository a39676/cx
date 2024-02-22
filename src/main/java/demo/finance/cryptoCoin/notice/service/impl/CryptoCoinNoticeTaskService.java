package demo.finance.cryptoCoin.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;

@Component
public class CryptoCoinNoticeTaskService extends CommonTaskService {

	@Autowired
	private CryptoCoinCommonNoticeService noticeService;

	@Scheduled(fixedDelay = 1000L * 30)
	public void cryptoCoinPriceNoticeHandler() {
		noticeService.noticeHandler();
	}

	@Scheduled(cron = "40 51 22 * * *")
	public void deleteOldNotice() {
		noticeService.deleteOldNotice();
	}

}
