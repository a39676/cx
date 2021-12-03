package demo.tool.calendarNotice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.tool.calendarNotice.service.CalendarNoticeService;

@Component
public class CalendarNoticeTaskService {

	@Autowired
	private CalendarNoticeService calendarNoticeService;
	
	@Scheduled(fixedRate = 1000L * 60 * 5)
	public void findAndSendNotice() {
		calendarNoticeService.findAndSendNotice();
	}
	
	@Scheduled(fixedRate = 1000L * 60 * 3)
	public void findAndSendStrongNotice() {
		calendarNoticeService.findAndSendStrongNotice();
	}
	
}
