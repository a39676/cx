package demo.tool.calendarNotice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.tool.calendarNotice.service.CalendarNoticeService;

@Component
public class CalendarNoticeTaskService {

	@Autowired
	private CalendarNoticeService calendarNoticeService;
	
	@Scheduled(fixedRate = 1000L * 60 * 10)
	public void visitCountRedisToOrm() {
		calendarNoticeService.findAndSendNotice();
	}
}
