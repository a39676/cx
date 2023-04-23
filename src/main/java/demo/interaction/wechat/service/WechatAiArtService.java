package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatAiArtService {

	EncryptDTO getImageWall(EncryptDTO encryptedDTO);

	EncryptDTO generateOtherLikeThat(EncryptDTO encryptedDTO);

}
