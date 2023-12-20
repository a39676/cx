package demo.tool.textMessageForward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.tool.textMessageForward.service.TextMessageForwardService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class TextMessageForwardServiceImpl extends CommonService implements TextMessageForwardService {

	@Autowired
	private TelegramService telegramService;

	@Override
	public CommonResult textMessageForwarding(ServiceMsgDTO dto) {
		CommonResult r = new CommonResult();

		telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE, dto.getMsg(),
				TelegramStaticChatID.MY_ID);
		r.setIsSuccess();
		return r;
	}
}
