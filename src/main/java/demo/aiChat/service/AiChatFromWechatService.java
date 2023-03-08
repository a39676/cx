package demo.aiChat.service;

import demo.thirdPartyAPI.openAI.pojo.vo.OpenAiUserDetailVO;

public interface AiChatFromWechatService {

	OpenAiUserDetailVO findUserDetailVoByWechatUid(String uid);

}
