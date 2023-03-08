package demo.aiChat.service;

import demo.aiChat.pojo.result.AiChatSendNewMessageResult;
import demo.aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatService {

	AiChatSendNewMessageResult sendNewChatMessage(Long aiChatUserId, String msg);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId);

}
