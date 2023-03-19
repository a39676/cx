package demo.aiChat.service;

import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatService {

	AiChatSendNewMessageResult sendNewChatMessage(Long aiChatUserId, String msg);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId);

}
