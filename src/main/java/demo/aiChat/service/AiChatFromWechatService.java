package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.GetAiChatHistoryResult;

public interface AiChatFromWechatService {

	AiChatSendNewMessageResult sendNewChatFromWechat(AiChatSendNewMsgDTO dto);

	GetAiChatHistoryResult findChatHistoryByAiChatUserIdToFrontEnd(String tmpKeyStr);

}
