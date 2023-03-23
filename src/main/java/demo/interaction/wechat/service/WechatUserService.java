package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatUserService {

	EncryptDTO getTmpKeyByOpenId(EncryptDTO dto);

	void extendTmpKeyValidity(EncryptDTO dto);

	Long __getWechatUserIdByOpenId(String openId);

	EncryptDTO buyMembershipFromWechat(EncryptDTO encryptedDTO);

}
