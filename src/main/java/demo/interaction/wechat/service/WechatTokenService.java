package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatTokenService {

	EncryptDTO getAccessToken(EncryptDTO encryptDTO);

	EncryptDTO updateAccessToken(EncryptDTO encryptedDTO);

}
