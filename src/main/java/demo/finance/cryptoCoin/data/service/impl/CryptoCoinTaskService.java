package demo.finance.cryptoCoin.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.finance.cryptoCoin.data.service.CryptoCoinForceOrderDataService;

@Component
public class CryptoCoinTaskService extends CommonTaskService {

//	@Autowired
//	private CryptoCoinDataComplexService cryptoCoinDataComplexService;
	@Autowired
	private CryptoCoinForceOrderDataService cryptoCoinForceOrderDataService;

//	@Scheduled(cron = "0 0 * * * ?")
//	public void sendBigMoveDataCrossResult() {
//		cryptoCoinDataComplexService.sendBigMoveDataCrossResult();
//	}

	@Scheduled(cron = "* */15 * * * ?")
	public void checkMostRecentForceOrderSummary() {
		cryptoCoinForceOrderDataService.checkMostRecentForceOrderSummary();
	}

}
