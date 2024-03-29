package demo.finance.currencyExchangeRate.data.service.impl;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.system.service.impl.SystemOptionService;
import demo.base.task.service.CommonTaskService;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Component
public class CurrencyExchangeRateDataTaskService extends CommonTaskService {

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;
	@Autowired
	private CurrencyExchangeRateNoticeService currencyExchangeRateNoticeService;
	@Autowired
	private SystemOptionService systemOptionService;

	@Scheduled(cron="40 05 22 * * *")
	@Scheduled(cron="40 05 10 * * *")
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
