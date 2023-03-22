package demo.interaction.wechatPay.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatPayService {

	EncryptDTO getWechatPayOptionDTO(EncryptDTO encrypt);

	EncryptDTO getJsApiTicket(EncryptDTO encryptDTO);

	EncryptDTO updateJsApiTicket(EncryptDTO encryptedDTO);

	EncryptDTO updateCertificate(EncryptDTO encryptedDTO);

	EncryptDTO getCertificate(EncryptDTO encryptDTO);

}
