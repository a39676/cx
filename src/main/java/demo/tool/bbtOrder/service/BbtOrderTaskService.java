package demo.tool.bbtOrder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.tool.bbtOrder.hsbc.service.HsbcService;
import demo.tool.bbtOrder.informationCollection.service.InformationCollectionService;

@Component
public class BbtOrderTaskService {

	@Autowired
	private InformationCollectionService informationCollectionService;
	@Autowired
	private HsbcService hsbcService;

	@Scheduled(cron="20 19 09 * * *")
	@Scheduled(cron="20 19 12 * * *")
	@Scheduled(cron="20 19 17 * * *")
	@Scheduled(cron="20 19 20 * * *")
	@Scheduled(cron="20 19 23 * * *")
	public void sendEducationInfomationCollectionTask() {
		informationCollectionService.sendEducationInfomationCollectionTask();
		informationCollectionService.sendV2exJobInfomationCollectionTask();
	}
	
	@Scheduled(fixedDelay = 1000L * 60 * 10)
	public void hsbcWechatPreregistRandomInsert() {
		hsbcService.hsbcWechatPreregistRandomInsert();
	}
}
