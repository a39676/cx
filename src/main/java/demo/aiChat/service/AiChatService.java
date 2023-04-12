package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;
import net.sf.json.JSONObject;

public interface AiChatService {

	AiChatSendNewMessageResult sendNewChatMessageWithHistory(Long aiChatUserId, AiChatSendNewMsgFromWechatDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(Long aiChatUserId);

	JSONObject sendNewChatMessageFromApi(Long aiChatUserId, AiChatSendNewMsgFromApiDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String aiChatUserPk);

}
