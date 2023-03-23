package demo.interaction.wechat.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import demo.interaction.wechat.service.WechatNotifyService;

@Service
public class WechatNotifyServiceImpl extends WechatCommonService implements WechatNotifyService {

	@Override
	public void sendNotify(EncryptDTO dto) {
		if (dto == null || StringUtils.isBlank(dto.getEncryptedStr())) {
			return;
		}
		String msg = decryptEncryptDTO(dto, String.class);
		if (msg == null) {
			sendTelegramMessage("收到 Wechat SDK 的通知, 但解码失败: " + dto.getEncryptedStr());
		} else {
			sendTelegramMessage(msg);
		}
	}
}
