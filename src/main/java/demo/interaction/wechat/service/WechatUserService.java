package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatUserService {

	EncryptDTO getTmpKeyByOpenId(EncryptDTO dto);

}
