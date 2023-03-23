package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;

public interface WechatNotifyService {

	void sendNotify(EncryptDTO dto);

}
