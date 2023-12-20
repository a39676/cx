package demo.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import demo.tool.textMessageForward.telegram.service.TelegramService;

public abstract class CommonMessageQueueReceiverService extends CommonService {

	@Autowired
	protected TelegramService telegramService;
}
