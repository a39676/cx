package demo.finance.currencyExchangeRate.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.finance.currencyExchangeRate.notice.service.CurrencyExchangeRateNoticeService;

@Component
public class CurrencyExchangeRateNoticeTaskService extends CommonTaskService {

	@Autowired
	private CurrencyExchangeRateNoticeService currencyExchangeRateNoticeService;

	@Scheduled(cron = "0 28 3 * * ?")
	public void deleteOldNotice() {
		currencyExchangeRateNoticeService.deleteOldNotice();
	}

}
