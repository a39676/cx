package demo.thirdPartyAPI.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.common.service.CommonService;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

public abstract class WechatCommonService extends CommonService {

	@Autowired
	protected WechatOptionService wechatOptionService;

	@Autowired
	private TelegramService telegramService;
	
	protected void sendTelegramMessage(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
	}
}
