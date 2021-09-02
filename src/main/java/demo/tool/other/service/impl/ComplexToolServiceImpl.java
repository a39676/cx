package demo.tool.other.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.other.service.ComplexToolService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;

@Service
public class ComplexToolServiceImpl extends CommonService implements ComplexToolService {
	
	@Autowired
	private TelegramService telegramService;
	
	@Override
	public void notificationBbtDown() {
		telegramService.sendMessage(TelegramBotType.BOT_2, "bbt down", TelegramStaticChatID.MY_ID);
	}
}
