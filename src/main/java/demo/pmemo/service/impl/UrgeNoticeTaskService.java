package demo.pmemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.pmemo.service.UrgeNoticeService;

@Component
public class UrgeNoticeTaskService {

	@Autowired
	private UrgeNoticeService urgeNoticeService;
	
	@Scheduled(fixedRate = 1000L * 60 * 60)
	public void sendUrgeNotice() {
		urgeNoticeService.sendAllUrgeNoticeList();
	}
}
