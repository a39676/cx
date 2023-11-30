package demo.tool.zulip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.zulip.service.ZulipToolService;

@Service
public class ZulipTaskService extends CommonService {

	@Autowired
	private ZulipToolService zulipToolService;
	
	@Scheduled(fixedDelay = 1000L * 60 * 50)
	public void deleteMessageHistoryAutomation() {
		zulipToolService.deleteMessageHistoryAutomation();
	}
}
