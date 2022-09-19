package demo.finance.currencyExchangeRate.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.currencyExchangeRate.data.service.CurrencyExchangeRateService;

@Component
public class CurrencyExchangeRateDataTaskToolServiceImpl extends CryptoCoinCommonService {

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;

	@Scheduled(cron = "0 10 3 * * ?")
	@Scheduled(cron = "0 10 2 * * ?")
	@Scheduled(cron = "0 10 1 * * ?")
	@Scheduled(cron = "0 10 0 * * ?")
	public void sendDailyDataQuery() {
		currencyExchangeRateService.sendDailyDataQuery();
	}

	@Scheduled(cron = "0 10 23 * * ?")
	@Scheduled(cron = "0 10 21 * * ?")
	@Scheduled(cron = "0 10 19 * * ?")
	@Scheduled(cron = "0 10 17 * * ?")
	@Scheduled(cron = "0 10 15 * * ?")
	@Scheduled(cron = "0 10 13 * * ?")
	@Scheduled(cron = "0 10 11 * * ?")
	@Scheduled(cron = "0 10 9 * * ?")
	@Scheduled(cron = "0 10 7 * * ?")
	@Scheduled(cron = "0 10 5 * * ?")
	public void sendDataQuery() {
		currencyExchangeRateService.sendDataQuery(false);
	}

}
