package demo.interaction.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.common.service.ToolCommonService;
import demo.tool.telegram.service.TelegramService;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;

public abstract class WechatCommonService extends ToolCommonService {

	@Autowired
	protected WechatOptionService wechatOptionService;

	@Autowired
	private TelegramService telegramService;

	protected void sendTelegramMessage(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.CX_MESSAGE, msg, TelegramStaticChatID.MY_ID);
	}

	protected <T> T decryptEncryptDTO(EncryptDTO encryptedDTO, Class<T> clazz) {
		try {
			String encryptedStr = encryptedDTO.getEncryptedStr();
			String decryptedStr = encryptUtil.aesDecrypt(wechatOptionService.getAesKey(),
					wechatOptionService.getAesInitVector(), encryptedStr);
			return buildObjFromJsonCustomization(decryptedStr, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	protected EncryptDTO encryptDTO(Object obj) {
		EncryptDTO dto = new EncryptDTO();
		String strNeedEncrypt = null;

		try {
			JSONObject json = JSONObject.fromObject(obj);
			strNeedEncrypt = json.toString();
		} catch (Exception e) {
			strNeedEncrypt = String.valueOf(obj);
		}
		try {
			String encryptedStr = encryptUtil.aesEncrypt(systemOptionService.getAesKey(),
					systemOptionService.getAesInitVector(), strNeedEncrypt);
			dto.setEncryptedStr(encryptedStr);
		} catch (Exception e) {
		}
		return dto;
	}
}
