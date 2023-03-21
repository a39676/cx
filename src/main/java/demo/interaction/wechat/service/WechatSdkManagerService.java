package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface WechatSdkManagerService {

	CommonResult refreshWechatSdkWechatOption();

	EncryptDTO getWechatSdkWechatOption(EncryptDTO dto);

}
