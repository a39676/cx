package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.finance.cryptoCoin.data.service.CryptoCoinDataComplexService;

@Component
public class CryptoCoinTaskService extends CommonTaskService {

	@Autowired
	private CryptoCoinDataComplexService cryptoCoinDataComplexService;

	@Scheduled(cron = "0 0 * * * ?")
	public void sendBigMoveDataCrossResult() {
		cryptoCoinDataComplexService.sendBigMoveDataCrossResult();
	}

	@Scheduled(cron = "* */15 * * * ?")
	public void checkMostRecentForceOrderSummary() {
		cryptoCoinDataComplexService.checkMostRecentForceOrderSummary();
	}

}
