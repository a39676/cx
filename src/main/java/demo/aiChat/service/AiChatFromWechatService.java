package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgDTO;
import demo.aiChat.pojo.result.AiChatSendNewMessageResult;

public interface AiChatFromWechatService {

	AiChatSendNewMessageResult sendNewChatFromWechat(AiChatSendNewMsgDTO dto);


}
