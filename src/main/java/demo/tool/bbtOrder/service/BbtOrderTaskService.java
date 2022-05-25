package demo.tool.bbtOrder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.tool.bbtOrder.informationCollection.service.InformationCollectionService;

@Component
public class BbtOrderTaskService {

	@Autowired
	private InformationCollectionService informationCollectionService;

	@Scheduled(cron="20 19 09 * * *")
	@Scheduled(cron="20 19 12 * * *")
	@Scheduled(cron="20 19 17 * * *")
	@Scheduled(cron="20 19 20 * * *")
	@Scheduled(cron="20 19 23 * * *")
	public void sendEducationInfomationCollectionTask() {
		informationCollectionService.sendEducationInfomationCollectionTask();
	}
}
