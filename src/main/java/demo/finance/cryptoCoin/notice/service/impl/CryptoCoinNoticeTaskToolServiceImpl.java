package demo.finance.cryptoCoin.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.notice.service.CryptoCoinCommonNoticeService;

@Component
public class CryptoCoinNoticeTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CryptoCoinCommonNoticeService noticeService;

	@Scheduled(fixedRate = 1000L * 30)
	public void cryptoCoinPriceNoticeHandler() {
		noticeService.noticeHandler();
	}

	@Scheduled(cron = "40 51 22 * * *")
	public void deleteOldNotice() {
		noticeService.deleteOldNotice();
	}

	@Scheduled(fixedRate = 40000L * 10)
	public void deleteNoticeByHitNoData() {
		noticeService.deleteNoticeByHitNoData();
	}
	
}
