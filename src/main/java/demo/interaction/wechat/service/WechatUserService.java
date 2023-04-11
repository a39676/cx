package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;

public interface WechatUserService {

	EncryptDTO getTmpKeyByOpenId(EncryptDTO dto);

	void extendTmpKeyValidity(EncryptDTO dto);

	Long __getWechatUserIdByOpenId(String openId);

	EncryptDTO buyMembershipFromWechat(EncryptDTO encryptedDTO);

	EncryptDTO getAiChatUserDetail(EncryptDTO encryptedDTO);

	EncryptDTO dailySignUp(EncryptDTO encryptedDTO);

	EncryptDTO recordingWechatUserFromParameterizedQrCode(EncryptDTO encrypedDTO);

	EncryptDTO getUserOpenIdList(EncryptDTO encryptedDTO);

	EncryptDTO generateNewApiKey(EncryptDTO encryptedDTO);

	EncryptDTO deleteApiKey(EncryptDTO encryptedDTO);

	EncryptDTO findAllApiKeysByAiChatUserId(EncryptDTO encryptedDTO);

	CommonResult bonusForNewPositiveUserInYesterday();

}
