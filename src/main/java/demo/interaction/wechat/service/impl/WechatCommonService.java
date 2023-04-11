package demo.interaction.wechat.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.common.service.ToolCommonService;
import demo.tool.telegram.service.TelegramService;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.type.TelegramBotType;

public abstract class WechatCommonService extends ToolCommonService {

	@Autowired
	protected WechatOptionService wechatOptionService;

	@Autowired
	private TelegramService telegramService;

	protected void sendTelegramMessage(String msg) {
		telegramService.sendMessageByChatRecordId(TelegramBotType.AI_CHAT_NOTICE, msg, TelegramStaticChatID.MY_ID);
	}

	@SuppressWarnings("unchecked")
	protected <T> T decryptEncryptDTO(EncryptDTO encryptedDTO, Class<T> clazz) {
		try {
			String encryptedStr = encryptedDTO.getEncryptedStr();
			String decryptedStr = encryptUtil.aesDecrypt(wechatOptionService.getAesKey(),
					wechatOptionService.getAesInitVector(), encryptedStr);
			if (String.class.equals(clazz)) {
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

		if (isBasicDataTypesOrString(obj)) {
			strNeedEncrypt = String.valueOf(obj);
		} else {
			try {
				Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter)
						.setPrettyPrinting().create();
				strNeedEncrypt = gson.toJson(obj);
			} catch (Exception e) {
				e.printStackTrace();
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

}
