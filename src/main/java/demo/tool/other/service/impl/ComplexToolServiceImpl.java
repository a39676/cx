package demo.tool.other.service.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.common.service.CommonService;
import demo.tool.other.pojo.vo.EncryptIdVO;
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
	
	@Override
	public EncryptIdVO encryptIDNum(Long id) {
		EncryptIdVO vo = new EncryptIdVO();
		vo.setPk(encryptId(id));
		vo.setUrlEncodePk(URLEncoder.encode(vo.getPk(), StandardCharsets.UTF_8));
		return vo;
	}
	
	@Override
	public Long decryptPK(String pk) {
		return decryptPrivateKey(pk);
	}
}
