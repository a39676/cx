package demo.interaction.wechat.service;

import auxiliaryCommon.pojo.dto.EncryptDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import wechatSdk.pojo.result.GetUserOpenIdListResult;

public interface WechatUserService {

	EncryptDTO getTmpKeyByOpenId(EncryptDTO dto);

	void extendTmpKeyValidity(EncryptDTO dto);

	Long __getWechatUserIdByOpenId(String openId);

	EncryptDTO buyMembershipFromWechat(EncryptDTO encryptedDTO);

	EncryptDTO getAiChatUserDetail(EncryptDTO encryptedDTO);

	EncryptDTO dailySignUp(EncryptDTO encryptedDTO);

	EncryptDTO recordingWechatUserFromParameterizedQrCode(EncryptDTO encrypedDTO);

	EncryptDTO getUserOpenIdListFromLocal(EncryptDTO encryptedDTO);

	EncryptDTO generateNewApiKey(EncryptDTO encryptedDTO);

	EncryptDTO deleteApiKey(EncryptDTO encryptedDTO);

	EncryptDTO findAllApiKeysByAiChatUserId(EncryptDTO encryptedDTO);

	CommonResult bonusForNewPositiveUserInYesterday();

	void compareLocalOpenIdListWithWechatOpenIdList();

	GetUserOpenIdListResult getOpenIdListFromWechatSdk();

	CommonResult __regOpenIdManual(String openId);

}
