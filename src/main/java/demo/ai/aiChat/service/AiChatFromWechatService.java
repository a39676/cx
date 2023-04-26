package demo.ai.aiChat.service;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import ai.aiChat.pojo.result.AiChatSendNewMessageResult;
import ai.aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatFromWechatService {

	AiChatSendNewMessageResult sendNewChatFromWechat(AiChatSendNewMsgFromWechatDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String tmpKeyStr);

}
