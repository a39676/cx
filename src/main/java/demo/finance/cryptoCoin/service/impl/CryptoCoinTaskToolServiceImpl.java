package demo.finance.cryptoCoin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.service.CryptoCoinNoticeService;
import demo.finance.cryptoCoin.service.CryptoCoinPriceService;

@Component
public class CryptoCoinTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CryptoCoinNoticeService noticeService;
	@Autowired
	private CryptoCoinPriceService priceService;

	@Scheduled(cron = "0 */1 * * * ?")
	public void cryptoCoinPriceNoticeHandler() {
		noticeService.noticeHandler();
	}

	@Scheduled(cron = "* 2 */1 * * ?")
	public void deleteExpiredCacheData() {
		priceService.deleteExpiredCacheData();
	}
}
