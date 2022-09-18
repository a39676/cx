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

	@Scheduled(cron="0 10 * * * ?")
	public void sendDailyDataQuery() {
		currencyExchangeRateService.sendDailyDataQuery();
	}

}
