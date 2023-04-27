package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatAiArtService {

	EncryptDTO getImageWallForWechat(EncryptDTO encryptedDTO);

	EncryptDTO generateOtherLikeThat(EncryptDTO encryptedDTO);

	EncryptDTO getParameterByJobPk(EncryptDTO encryptedDTO);

}
