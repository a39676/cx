package demo.joy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.joy.garden.service.JoyGradenInfoService;

@Component
public class JoyTaskService extends CommonTaskService {

	@Autowired
	private JoyGradenInfoService infoService;

	@Scheduled(fixedDelay = 1000L * 60 * 30)
	public void cacheToDatabase() {
		infoService.cacheToDatabase();
	}

}
