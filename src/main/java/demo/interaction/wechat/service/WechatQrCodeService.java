package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatQrCodeService {

	EncryptDTO receiveLongLiveQrCodeResult(EncryptDTO encryptedDTO);

}
