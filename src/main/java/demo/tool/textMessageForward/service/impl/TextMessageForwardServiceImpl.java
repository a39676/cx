package demo.tool.textMessageForward.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.ServiceMsgDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.common.service.CommonService;
import demo.config.costom_component.BbtDynamicKey;
import demo.tool.telegram.service.TelegramService;
import demo.tool.textMessageForward.service.TextMessageForwardService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class TextMessageForwardServiceImpl extends CommonService implements TextMessageForwardService {

	@Autowired
	private TelegramService telegramService;
	@Autowired
	private BbtDynamicKey bbtDynamicKey;

	@Override
	public CommonResult textMessageForwarding(ServiceMsgDTO dto) {
		CommonResult r = new CommonResult();
		String keyInput = dto.getKey();
		if (!bbtDynamicKey.isCorrectKey(keyInput)) {
			return r;
		}

		telegramService.sendMessageByChatRecordId(TelegramBotType.BBT_MESSAGE, dto.getMsg(),
				TelegramStaticChatID.MY_ID);
		r.setIsSuccess();
		return r;
	}
}
