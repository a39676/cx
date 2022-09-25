package demo.finance.currencyExchangeRate.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Component
public class CurrencyExchangeRateNoticeTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CurrencyExchangeRateNoticeService currencyExchangeRateNoticeService;

	@Scheduled(cron = "0 28 3 * * ?")
	public void sendDataQuery() {
		currencyExchangeRateNoticeService.deleteOldNotice();
	}

}
