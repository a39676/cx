package demo.interaction.wechat.service;

import aiChat.pojo.result.GetTmpKeyByOpenIdResult;
import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatUserService {

	GetTmpKeyByOpenIdResult getTmpKeyByOpenId(EncryptDTO dto);


}
