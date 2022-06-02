package demo.joy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.joy.garden.service.JoyGradenInfoService;

@Component
public class JoyTaskServiceImpl {

	@Autowired
	private JoyGradenInfoService infoService;

	@Scheduled(cron = "* */30 * * * ?")
	public void cacheToDatabase() {
		infoService.cacheToDatabase();
	}

}
