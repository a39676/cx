package demo.aiChat.service;

import demo.aiChat.pojo.result.CreateAiChatUserResult;

public interface AiChatUserService {

	CreateAiChatUserResult createAiChatUserDetailBySystemUserId(Long systemUserId);

	CreateAiChatUserResult createAiChatUserDetailByWechatUid(Long wechatUserLongId, String wechatOid);

	Long createNewTmpKey(Long wechatUserId, String openId);

}
