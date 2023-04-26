package demo.ai.aiChat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.ai.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AiChatCacheService extends CommonService {

	private Map<String, Long> openIdMatchAiChatUserIdMap = new HashMap<>();
	private Map<Long, Long> systemUserIdMatchAiChatUserIdMap = new HashMap<>();
	/**
	 * key:aiChatUserId value:AiChatUserMembershipDetailSummaryDTO
	 */
	private Map<Long, AiChatUserMembershipDetailSummaryDTO> membershipCacheMap = new HashMap<>();
	/**
	 * key:API key encrypted, value: AI chat user id TODO update this, when create / delete
	 * API key
	 */
	private Map<String, Long> apiKeyCacheMap = new HashMap<>();

	/**
	 * key:AI chat user ID, value: counter TODO clean it daily
	 */
	private Map<Long, Integer> apiKeyOperationCounterDaily = new HashMap<>();

	public Map<String, Long> getOpenIdMatchAiChatUserIdMap() {
		return openIdMatchAiChatUserIdMap;
	}

	public void setOpenidMatchAiChatUserIdMap(Map<String, Long> openIdMatchAiChatUserIdMap) {
		this.openIdMatchAiChatUserIdMap = openIdMatchAiChatUserIdMap;
	}

	public Map<Long, Long> getSystemUserIdMatchAiChatUserIdMap() {
		return systemUserIdMatchAiChatUserIdMap;
	}

	public void setSystemUserIdMatchAiChatUserIdMap(Map<Long, Long> systemUserIdMatchAiChatUserIdMap) {
		this.systemUserIdMatchAiChatUserIdMap = systemUserIdMatchAiChatUserIdMap;
	}

	public Map<Long, AiChatUserMembershipDetailSummaryDTO> getMembershipCacheMap() {
		return membershipCacheMap;
	}

	public void setMembershipCacheMap(Map<Long, AiChatUserMembershipDetailSummaryDTO> membershipCacheMap) {
		this.membershipCacheMap = membershipCacheMap;
	}

	public Map<String, Long> getApiKeyCacheMap() {
		return apiKeyCacheMap;
	}

	public void setApiKeyCacheMap(Map<String, Long> apiKeyCacheMap) {
		this.apiKeyCacheMap = apiKeyCacheMap;
	}

	public void setOpenIdMatchAiChatUserIdMap(Map<String, Long> openIdMatchAiChatUserIdMap) {
		this.openIdMatchAiChatUserIdMap = openIdMatchAiChatUserIdMap;
	}

	public Map<Long, Integer> getApiKeyOperationCounterDaily() {
		return apiKeyOperationCounterDaily;
	}

	public void setApiKeyOperationCounterDaily(Map<Long, Integer> apiKeyOperationCounterDaily) {
		this.apiKeyOperationCounterDaily = apiKeyOperationCounterDaily;
	}

	@Override
	public String toString() {
		return "AiChatCacheService [openIdMatchAiChatUserIdMap=" + openIdMatchAiChatUserIdMap
				+ ", systemUserIdMatchAiChatUserIdMap=" + systemUserIdMatchAiChatUserIdMap + ", membershipCacheMap="
				+ membershipCacheMap + ", apiKeyCacheMap=" + apiKeyCacheMap + ", apiKeyOperationCounterDaily="
				+ apiKeyOperationCounterDaily + "]";
	}

}
