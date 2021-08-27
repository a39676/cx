package demo.tool.other.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.constant.ServerHost;
import demo.common.service.CommonService;
import demo.tool.other.service.ComplexToolService;
import demo.tool.telegram.pojo.constant.TelegramStaticChatID;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import toolPack.httpHandel.HttpUtil;

@Service
public class ComplexToolServiceImpl extends CommonService implements ComplexToolService {
	
	@Autowired
	private HttpUtil httpUtil;
	@Autowired
	private TelegramService telegramService;
	
	@Override
	public void pingBBT() {
		String url = ServerHost.localHost10002 + "/ping/ping";
		try {
			String response = String.valueOf(httpUtil.sendGet(url));
			if(response.contains("pong")) {
				return;
			}
		} catch (Exception e) {
		}
		telegramService.sendMessage(TelegramBotType.BOT_2, "bbt down", TelegramStaticChatID.MY_ID);
	}
}
