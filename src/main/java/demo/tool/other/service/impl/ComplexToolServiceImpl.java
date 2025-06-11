package demo.tool.other.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.type.HeartBeatType;
import demo.common.service.ToolCommonService;
import demo.tool.other.pojo.vo.EncryptIdVO;
import demo.tool.other.service.ComplexToolService;
import demo.tool.textMessageForward.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

@Service
public class ComplexToolServiceImpl extends ToolCommonService implements ComplexToolService {

	@Autowired
	private TelegramService telegramService;

	@Override
	public void notificationServiceDown(HeartBeatType heartBeatType) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, heartBeatType.getName() + " down",
				TelegramStaticChatID.MY_ID);
	}

	@Override
	public EncryptIdVO encryptIDNum(Long id) {
		EncryptIdVO vo = new EncryptIdVO();
		vo.setPk(systemOptionService.encryptId(id));
		vo.setUrlEncodePk(URLEncoder.encode(vo.getPk(), StandardCharsets.UTF_8));
		return vo;
	}

	@Override
	public Long decryptPK(String pk) {
		return systemOptionService.decryptPrivateKey(pk);
	}
}
