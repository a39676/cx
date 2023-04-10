package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import aiChat.pojo.result.AiChatSendNewMessageResult;
import aiChat.pojo.result.FindAllApiKeysResult;
import aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;

public interface AiChatFromApiService {

	AiChatSendNewMessageResult sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto);

	GenerateNewApiKeyResult generateNewApiKey(String tmpKey);

	FindAllApiKeysResult findAllApiKeysByAiChatUserId(String tmpKey);

	CommonResult deleteApiKey(String tmpKey, String apiKey);

}
