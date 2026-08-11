package demo.tool.textMessageForward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.task.service.CommonTaskService;
import demo.tool.textMessageForward.service.TelegramMeetingMsgForwardService;

@Component
public class TelegramMeetingMsgForwardTaskService extends CommonTaskService {

	@Autowired
	private TelegramMeetingMsgForwardService telegramMeetingMsgForwardService;

	// 10mins + 1s
	@Scheduled(fixedRate = 1000L * 60 * 10 + 1000L)
	public void sendUrgeNotice() {
		telegramMeetingMsgForwardService.findMeetingMsgAndForward();
	}
}
