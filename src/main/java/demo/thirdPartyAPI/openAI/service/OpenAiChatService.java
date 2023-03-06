package demo.thirdPartyAPI.openAI.service;

import demo.thirdPartyAPI.openAI.pojo.vo.OpenAiUserDetailVO;

public interface OpenAiChatService {

	OpenAiUserDetailVO findUserDetailVoByWechatUid(String uid);

}
