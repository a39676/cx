package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.finance.cryptoCoin.data.service.CryptoCoinPriceCacheService;

@Component
public class CryptoCoinTaskService extends CommonTaskService {

	@Autowired
	private CryptoCoinPriceCacheService cacheService;
	@Autowired
	protected CryptoCoinOptionService optionService;

	@Scheduled(fixedDelay = 60000)
	public void cleanOldHistoryData() {
		cacheService.cleanOldHistoryData();
	}

}
