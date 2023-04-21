package demo.ai.aiChat.service;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import ai.aiChat.pojo.result.FindAllApiKeysResult;
import ai.aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface AiChatFromApiService {

	GenerateNewApiKeyResult generateNewApiKey(String tmpKey);

	FindAllApiKeysResult findAllApiKeysByAiChatUserId(String tmpKey);

	CommonResult deleteApiKey(String tmpKey, String apiKey);

	JSONObject sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto);

}
