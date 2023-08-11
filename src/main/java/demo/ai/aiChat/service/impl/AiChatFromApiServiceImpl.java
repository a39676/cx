package demo.ai.aiChat.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import ai.aiChat.pojo.result.FindAllApiKeysResult;
import ai.aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.ai.aiChat.pojo.po.AiChatApiKey;
import demo.ai.aiChat.pojo.po.AiChatApiKeyExample;
import demo.ai.aiChat.service.AiChatFromApiService;
import demo.ai.aiChat.service.AiChatService;
import demo.ai.common.service.impl.AiCommonService;
import net.sf.json.JSONObject;
import openAi.pojo.dto.OpenAiChatCompletionErrorResponseErrorDTO;
import openAi.pojo.dto.OpenAiChatCompletionResponseDTO;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class AiChatFromApiServiceImpl extends AiCommonService implements AiChatFromApiService {

	@Autowired
	private AiChatService aiChatService;

	@Override
	public GenerateNewApiKeyResult generateNewApiKey(String tmpKey) {
		GenerateNewApiKeyResult r = new GenerateNewApiKeyResult();

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiChatUserId == null) {
			r.setMessage("过期 ID, 请先退出后重新登录");
			return r;
		}

		Integer operationsCounter = aiChatCacheService.getApiKeyOperationCounterDaily().get(aiChatUserId);
		if (operationsCounter == null) {
			operationsCounter = 0;
		} else if (operationsCounter > aiChatOptionService.getMaxCountOfapiKeyOperations()) {
			r.setMessage("无法频繁创建/删除API key, 请明天再尝试");
			return r;
		}

		AiChatApiKeyExample example = new AiChatApiKeyExample();
		example.createCriteria().andAiChatUserIdEqualTo(aiChatUserId).andIsDeleteEqualTo(false);
		List<AiChatApiKey> apiKeyPoList = apiKeyMapper.selectByExample(example);
		if (apiKeyPoList.size() >= aiChatOptionService.getMaxCountOfApiKey()) {
			r.setMessage("Too many API keys. Please delete one of them before generate new one");
			return r;
		}

		Long newApiKeyDecrypt = snowFlake.getNextId();
		String newApiKey = systemOptionService.encryptId(newApiKeyDecrypt);

		AiChatApiKey row = new AiChatApiKey();
		row.setAiChatUserId(aiChatUserId);
		row.setApiKeyDecrypt(newApiKeyDecrypt);
		int insertCount = apiKeyMapper.insertSelective(row);

		if (insertCount != 1) {
			r.setMessage("Generate API key error, please try again later");
			sendTelegramMessage("无法创建 AI chat API key");
			return r;
		}

		aiChatCacheService.getApiKeyOperationCounterDaily().put(aiChatUserId, operationsCounter + 1);
		aiChatCacheService.getApiKeyCacheMap().put(newApiKey, aiChatUserId);

		if (operationsCounter > aiChatOptionService.getMaxCountOfapiKeyOperations() / 2) {
			r.setMessage("请勿频繁创建/删除API key");
		}
		r.setApiKey(newApiKey);
		r.setIsSuccess();
		return r;
	}

	@Override
	public CommonResult deleteApiKey(String tmpKey, String apiKey) {
		CommonResult r = new CommonResult();
		if (StringUtils.isBlank(apiKey)) {
			r.setIsSuccess();
			return r;
		}

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiChatUserId == null) {
			r.setMessage("过期 ID, 请先重新登录");
			return r;
		}

		Long decryptApiKey = systemOptionService.decryptPrivateKey(apiKey);
		if (decryptApiKey == null) {
			r.setIsSuccess();
			return r;
		}

		AiChatApiKeyExample example = new AiChatApiKeyExample();
		example.createCriteria().andAiChatUserIdEqualTo(aiChatUserId).andApiKeyDecryptEqualTo(decryptApiKey)
				.andIsDeleteEqualTo(false);
		AiChatApiKey row = new AiChatApiKey();
		row.setIsDelete(true);
		int deleteCount = apiKeyMapper.updateByExampleSelective(row, example);
		if (deleteCount == 1) {
			Integer operationsCounter = aiChatCacheService.getApiKeyOperationCounterDaily().get(aiChatUserId);
			if (operationsCounter == null) {
				operationsCounter = 0;
			}
			aiChatCacheService.getApiKeyOperationCounterDaily().put(aiChatUserId, operationsCounter + 1);
			aiChatCacheService.getApiKeyCacheMap().remove(apiKey);
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public FindAllApiKeysResult findAllApiKeysByAiChatUserId(String tmpKey) {
		FindAllApiKeysResult r = new FindAllApiKeysResult();

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiChatUserId == null) {
			r.setMessage("过期 ID, 请先退出后重新登录");
			return r;
		}

		AiChatApiKeyExample example = new AiChatApiKeyExample();
		example.createCriteria().andAiChatUserIdEqualTo(aiChatUserId).andIsDeleteEqualTo(false);
		List<AiChatApiKey> apiKeyPoList = apiKeyMapper.selectByExample(example);
		if (apiKeyPoList.isEmpty()) {
			r.setIsSuccess();
			return r;
		}

		List<String> apiKeyList = new ArrayList<>();
		String apiKeyEncrypted = null;
		for (AiChatApiKey po : apiKeyPoList) {
			apiKeyEncrypted = systemOptionService.encryptId(po.getApiKeyDecrypt());
			apiKeyList.add(apiKeyEncrypted);
			aiChatCacheService.getApiKeyCacheMap().put(apiKeyEncrypted, aiChatUserId);
		}
		r.setApiKeyList(apiKeyList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public OpenAiChatCompletionResponseDTO sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto) {
		OpenAiChatCompletionResponseDTO r = new OpenAiChatCompletionResponseDTO();
		OpenAiChatCompletionErrorResponseErrorDTO errorMsg = new OpenAiChatCompletionErrorResponseErrorDTO();

		if (StringUtils.isBlank(dto.getApiKey())) {
			errorMsg.setMessage("API key error");
			r.setError(errorMsg);
			return r;
		}

		Long aiChatUserId = getAiUserIdByApiKey(dto.getApiKey());
		AiChatApiKey po = null;
		Long apiKeyDecrypt = systemOptionService.decryptPrivateKey(dto.getApiKey());

		if (aiChatUserId == null) {
			errorMsg.setMessage("API key expired, please generate a new one");
			r.setError(errorMsg);
			return r;
		}

		po = apiKeyMapper.selectByPrimaryKey(apiKeyDecrypt);
		po.setLastUsedTime(LocalDateTime.now());
		apiKeyMapper.updateByPrimaryKeySelective(po);

		OpenAiChatCompletionResponseDTO apiResult = aiChatService.sendNewChatMessageFromApi(aiChatUserId, dto);

		if (apiResult.getError() != null) {
			LocalDateTime now = LocalDateTime.now();
			String storePrefixPath = aiChatOptionService.getChatFromApiStorePrefixPath();
			String storePathStr = storePrefixPath + File.separator + aiChatUserId + File.separator
					+ localDateTimeHandler.dateToStr(now, DateTimeUtilCommon.dateTimeFormatNoSymbol) + File.separator
					+ snowFlake.getNextId() + ".txt";

			JSONObject inputDTO = JSONObject.fromObject(dto);
			JSONObject jsonForStore = new JSONObject();
			jsonForStore.put("input", inputDTO);
			jsonForStore.put("output", apiResult);

			FileUtilCustom fileUtil = new FileUtilCustom();
			fileUtil.byteToFile(jsonForStore.toString(), storePathStr);
		}

		return apiResult;
	}
}
