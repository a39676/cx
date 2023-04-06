package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgFromWechatDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatFromWechatService {

	AiChatSendNewMessageResult sendNewChatFromWechat(AiChatSendNewMsgFromWechatDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String tmpKeyStr);

}
