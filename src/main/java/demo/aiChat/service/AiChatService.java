package demo.aiChat.service;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import ai.aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import ai.aiChat.pojo.result.AiChatSendNewMessageResult;
import ai.aiChat.pojo.result.GetAiChatHistoryResult;
import net.sf.json.JSONObject;

public interface AiChatService {

	AiChatSendNewMessageResult sendNewChatMessageWithHistory(Long aiChatUserId, AiChatSendNewMsgFromWechatDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId);

	JSONObject sendNewChatMessageFromApi(Long aiChatUserId, AiChatSendNewMsgFromApiDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String aiChatUserPk);

}
