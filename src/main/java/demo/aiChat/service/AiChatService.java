package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatService {

	AiChatSendNewMessageResult sendNewChatMessageWithHistory(Long aiChatUserId, AiChatSendNewMsgFromWechatDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId);

	AiChatSendNewMessageResult sendNewChatMessageFromApi(Long aiChatUserId, AiChatSendNewMsgFromApiDTO dto);

}
