package demo.aiChat.service;

import aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import aiChat.pojo.result.FindAllApiKeysResult;
import aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;
import net.sf.json.JSONObject;

public interface AiChatFromApiService {

	GenerateNewApiKeyResult generateNewApiKey(String tmpKey);

	FindAllApiKeysResult findAllApiKeysByAiChatUserId(String tmpKey);

	CommonResult deleteApiKey(String tmpKey, String apiKey);

	JSONObject sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto);

}
