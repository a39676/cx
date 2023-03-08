package demo.aiChat.service;

import auxiliaryCommon.pojo.result.CommonResult;

public interface AiChatUserService {
	
	CommonResult createAiChatUserDetailBySystemUserId(Long systemUserId);

	CommonResult createAiChatUserDetailByWechatUid(String wechatUid);

}
