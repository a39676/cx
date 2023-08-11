package demo.ai.aiChat.service;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import ai.aiChat.pojo.result.FindAllApiKeysResult;
import ai.aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;
import openAi.pojo.dto.OpenAiChatCompletionResponseDTO;

public interface AiChatFromApiService {

	GenerateNewApiKeyResult generateNewApiKey(String tmpKey);

	FindAllApiKeysResult findAllApiKeysByAiChatUserId(String tmpKey);

	CommonResult deleteApiKey(String tmpKey, String apiKey);

	OpenAiChatCompletionResponseDTO sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto);

}
