package demo.aiChat.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aiChat.pojo.dto.AiChatSendNewMsgFromApiDTO;
import aiChat.pojo.result.FindAllApiKeysResult;
import aiChat.pojo.result.GenerateNewApiKeyResult;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.aiChat.mapper.AiChatApiKeyMapper;
import demo.aiChat.pojo.po.AiChatApiKey;
import demo.aiChat.pojo.po.AiChatApiKeyExample;
import demo.aiChat.service.AiChatFromApiService;
import demo.aiChat.service.AiChatService;
import net.sf.json.JSONObject;

@Service
public class AiChatFromApiServiceImpl extends AiChatCommonService implements AiChatFromApiService {

	@Autowired
	private AiChatService aiChatService;
	@Autowired
	private AiChatApiKeyMapper apiKeyMapper;

	@Override
	public GenerateNewApiKeyResult generateNewApiKey(String tmpKey) {
		GenerateNewApiKeyResult r = new GenerateNewApiKeyResult();

		Long aiChatUserId = getAiChatUserIdByTempKey(tmpKey);
		if (aiChatUserId == null) {
			r.setMessage("过期 ID, 请先退出后重新登录");
			return r;
		}

		Integer operationsCounter = cacheService.getApiKeyOperationCounterDaily().get(aiChatUserId);
		if (operationsCounter == null) {
			operationsCounter = 0;
		} else if (operationsCounter > optionService.getMaxCountOfapiKeyOperations()) {
			r.setMessage("无法频繁创建/删除API key, 请明天再尝试");
			return r;
		}

		AiChatApiKeyExample example = new AiChatApiKeyExample();
		example.createCriteria().andAiChatUserIdEqualTo(aiChatUserId).andIsDeleteEqualTo(false);
		List<AiChatApiKey> apiKeyPoList = apiKeyMapper.selectByExample(example);
		if (apiKeyPoList.size() >= optionService.getMaxCountOfApiKey()) {
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

		cacheService.getApiKeyOperationCounterDaily().put(aiChatUserId, operationsCounter + 1);
		cacheService.getApiKeyCacheMap().put(newApiKey, aiChatUserId);

		if (operationsCounter > optionService.getMaxCountOfapiKeyOperations() / 2) {
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
			Integer operationsCounter = cacheService.getApiKeyOperationCounterDaily().get(aiChatUserId);
			if (operationsCounter == null) {
				operationsCounter = 0;
			}
			cacheService.getApiKeyOperationCounterDaily().put(aiChatUserId, operationsCounter + 1);
			cacheService.getApiKeyCacheMap().remove(apiKey);
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
			cacheService.getApiKeyCacheMap().put(apiKeyEncrypted, aiChatUserId);
		}
		r.setApiKeyList(apiKeyList);
		r.setIsSuccess();
		return r;
	}

	@Override
	public JSONObject sendNewChatMessage(AiChatSendNewMsgFromApiDTO dto) {
		JSONObject r = new JSONObject();
		JSONObject errorMsg = new JSONObject();

		log.error("Receive dto: " + dto.toString());
		
		if (StringUtils.isBlank(dto.getApiKey())) {
			errorMsg.put("message", "API key error");
			r.put("error", errorMsg);
			return r;
		}

		Long aiChatUserId = cacheService.getApiKeyCacheMap().get(dto.getApiKey());
		log.error("get ai chat ueser id from cache: " + aiChatUserId);
		AiChatApiKey po = null;
		Long apiKeyDecrypt = systemOptionService.decryptPrivateKey(dto.getApiKey());

		if (aiChatUserId == null) {
			if (apiKeyDecrypt == null) {
				errorMsg.put("message", "API key expired, please generate a new one");
				r.put("error", errorMsg);
				return r;
			}

			po = apiKeyMapper.selectByPrimaryKey(apiKeyDecrypt);
			if (po == null || po.getIsDelete()) {
				log.error("API key record is null or is delete");
				errorMsg.put("message", "API key expired, please generate a new one");
				r.put("error", errorMsg);
				return r;
			}
			aiChatUserId = po.getAiChatUserId();
			cacheService.getApiKeyCacheMap().put(dto.getApiKey(), aiChatUserId);
		}

		po = apiKeyMapper.selectByPrimaryKey(apiKeyDecrypt);
		po.setLastUsedTime(LocalDateTime.now());
		apiKeyMapper.updateByPrimaryKeySelective(po);

		return aiChatService.sendNewChatMessageFromApi(aiChatUserId, dto);
	}
}
