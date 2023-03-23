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

	@SuppressWarnings("unchecked")
	protected <T> T decryptEncryptDTO(EncryptDTO encryptedDTO, Class<T> clazz) {
		try {
			String encryptedStr = encryptedDTO.getEncryptedStr();
			String decryptedStr = encryptUtil.aesDecrypt(wechatOptionService.getAesKey(),
					wechatOptionService.getAesInitVector(), encryptedStr);
			if(String.class.equals(clazz)) {
				return (T) decryptedStr;
			}
			return buildObjFromJsonCustomization(decryptedStr, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	protected EncryptDTO encryptDTO(Object obj) {
		EncryptDTO dto = new EncryptDTO();
		String strNeedEncrypt = null;

		if (isBasicDataTypes(obj)) {
			strNeedEncrypt = String.valueOf(obj);
		} else {
			try {
				JSONObject json = JSONObject.fromObject(obj);
				strNeedEncrypt = json.toString();
			} catch (Exception e) {
				strNeedEncrypt = String.valueOf(obj);
			}
		}
		try {
			String encryptedStr = encryptUtil.aesEncrypt(wechatOptionService.getAesKey(),
					wechatOptionService.getAesInitVector(), strNeedEncrypt);
			dto.setEncryptedStr(encryptedStr);
		} catch (Exception e) {
		}
		return dto;
	}
	
	private boolean isBasicDataTypes(Object obj) {
		return obj instanceof Boolean || obj instanceof Byte || obj instanceof Short
		|| obj instanceof Integer || obj instanceof Long || obj instanceof Float
		|| obj instanceof Double || obj instanceof Character;
	}
}
