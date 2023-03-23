package demo.aiChat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.common.service.CommonService;

@Scope("singleton")
@Service
public class AiChatCacheService extends CommonService {

	private Map<String, Long> openIdMatchAiChatUserIdMap = new HashMap<>();
	private Map<Long, Long> systemUserIdMatchAiChatUserIdMap = new HashMap<>();
	/**
	 * TODO 当触发购买会员时需要刷新 凌晨清空
	 * key:aiChatUserId
	 * value:AiChatUserMembershipDetailSummaryDTO
	 */
	private Map<Long, AiChatUserMembershipDetailSummaryDTO> membershipCacheMap = new HashMap<>();


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


}
