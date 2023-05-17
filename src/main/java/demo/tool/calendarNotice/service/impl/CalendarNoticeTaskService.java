package demo.tool.calendarNotice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.tool.calendarNotice.service.CalendarNoticeService;

@Component
public class CalendarNoticeTaskService extends CommonTaskService {

	@Autowired
	private CalendarNoticeService calendarNoticeService;

	@Scheduled(fixedDelay = 1000L * 60 * 5)
	public void findAndSendNotice() {
		calendarNoticeService.findAndSendNotice();
	}

	@Scheduled(fixedDelay = 1000L * 60 * 3)
	public void findAndSendStrongNotice() {
		calendarNoticeService.findAndSendStrongNotice();
	}

	@Scheduled(cron = "01 30 20 * * *")
	public void sendTomorrowNoticeList() {
		calendarNoticeService.sendTomorrowNoticeList();
	}

}
