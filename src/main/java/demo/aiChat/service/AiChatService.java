package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatService {

	AiChatSendNewMessageResult sendNewChatMessage(Long aiChatUserId, AiChatSendNewMsgDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId);

}
