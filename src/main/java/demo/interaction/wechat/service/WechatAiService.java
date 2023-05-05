package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatAiService {

	EncryptDTO sendNewMessage(EncryptDTO encryptedDTO);

	EncryptDTO findChatHistoryByAiChatUserIdToFrontEnd(EncryptDTO encryptedDTO);

	EncryptDTO getAiServiceCache();

	EncryptDTO sendTextToImg(EncryptDTO encryptedDTO);

	EncryptDTO getJobResultListByTmpKey(EncryptDTO encryptedDTO);

}
