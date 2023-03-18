package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatAiChatService {

	EncryptDTO sendNewMessage(EncryptDTO encryptedDTO);

}
