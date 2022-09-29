package demo.tool.other.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.tool.other.pojo.vo.EncryptIdVO;
import demo.tool.other.service.ComplexToolService;
import demo.tool.service.impl.ToolCommonService;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

@Service
public class ComplexToolServiceImpl extends ToolCommonService implements ComplexToolService {
	
	@Autowired
	private TelegramService telegramService;
	
	@Override
	public void notificationBbtDown() {
		telegramService.sendMessageByChatRecordId(TelegramBotType.BOT_2, "bbt down", TelegramStaticChatID.MY_ID);
	}
	
	@Override
	public EncryptIdVO encryptIDNum(Long id) {
		EncryptIdVO vo = new EncryptIdVO();
		vo.setPk(systemConstantService.encryptId(id));
		vo.setUrlEncodePk(URLEncoder.encode(vo.getPk(), StandardCharsets.UTF_8));
		return vo;
	}
	
	@Override
	public Long decryptPK(String pk) {
		return systemConstantService.decryptPrivateKey(pk);
	}
}
