package demo.finance.currencyExchangeRate.data.service.impl;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Component
public class CurrencyExchangeRateDataTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;
	@Autowired
	private CurrencyExchangeRateNoticeService currencyExchangeRateNoticeService;

	@Scheduled(cron = "0 10 * * * ?")
	public void sendDataQuery() {
		if(systemOptionService.isDev()) {
			return;
		}
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
		if(hour >= 0 && hour <= 3) {
			currencyExchangeRateService.sendDailyDataQuery();
		} else {
			currencyExchangeRateService.sendDataQuery(false);
		}
		currencyExchangeRateNoticeService.noticeHandler();
	}

}
