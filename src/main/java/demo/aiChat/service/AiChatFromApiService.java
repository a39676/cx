package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.FindAllApiKeysResult;
import aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;

public interface AiChatFromApiService {

	AiChatSendNewMessageResult sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto);

	GenerateNewApiKeyResult generateNewApiKey(Long aiChatUserId);

	CommonResult deleteApiKey(Long aiChatUserId, String apiKey);

	FindAllApiKeysResult findAllApiKeysByAiChatUserId(Long aiChatUserId);

}
