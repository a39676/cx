package demo.aiChat.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.aiChat.pojo.dto.AiChatAesOptionDTO;
import demo.aiChat.pojo.dto.AiChatUserMembershipDetailSummaryDTO;
import demo.aiChat.pojo.dto.AiChatUserMembershipLevelDetailDTO;
import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AiChatOptionService extends CommonService {

	@Value("${optionFilePath.aiChat}")
	private String optionFilePath;
	private String chatStorePrefixPath;
	private Integer chatHistorySaveCountingLimit = 100;

	private Map<Integer, AiChatAesOptionDTO> aesOptionMap;

	private List<AiChatUserMembershipLevelDetailDTO> membershipLevelDetail;
	private Map<String, Long> uidMatchAiChatUserIdMap = new HashMap<>();
	private Map<Long, Long> systemUserIdMatchAiChatUserIdMap = new HashMap<>();

	/*
	 * TODO 当触发购买会员时需要刷新 凌晨清空
	 */
	private Map<Long, AiChatUserMembershipDetailSummaryDTO> membershipCacheMap = new HashMap<>();

	public String getChatStorePrefixPath() {
		return chatStorePrefixPath;
	}

	public void setChatStorePrefixPath(String chatStorePrefixPath) {
		this.chatStorePrefixPath = chatStorePrefixPath;
	}

	public Integer getChatHistorySaveCountingLimit() {
		return chatHistorySaveCountingLimit;
	}

	public void setChatHistorySaveCountingLimit(Integer chatHistorySaveCountingLimit) {
		this.chatHistorySaveCountingLimit = chatHistorySaveCountingLimit;
	}

	public Map<Integer, AiChatAesOptionDTO> getAesOptionMap() {
		return aesOptionMap;
	}

	public void setAesOptionMap(Map<Integer, AiChatAesOptionDTO> aesOptionMap) {
		this.aesOptionMap = aesOptionMap;
	}

	public List<AiChatUserMembershipLevelDetailDTO> getMembershipLevelDetail() {
		return membershipLevelDetail;
	}

	public void setMembershipLevelDetail(List<AiChatUserMembershipLevelDetailDTO> membershipLevelDetail) {
		this.membershipLevelDetail = membershipLevelDetail;
	}

	public Map<String, Long> getUidMatchAiChatUserIdMap() {
		return uidMatchAiChatUserIdMap;
	}

	public void setUidMatchAiChatUserIdMap(Map<String, Long> uidMatchAiChatUserIdMap) {
		this.uidMatchAiChatUserIdMap = uidMatchAiChatUserIdMap;
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

	@Override
	public String toString() {
		return "AiChatOptionService [chatStorePrefixPath=" + chatStorePrefixPath + ", chatHistorySaveCountingLimit="
				+ chatHistorySaveCountingLimit + ", aesOptionMap=" + aesOptionMap + ", membershipLevelDetail="
				+ membershipLevelDetail + ", uidMatchAiChatUserIdMap=" + uidMatchAiChatUserIdMap
				+ ", systemUserIdMatchAiChatUserIdMap=" + systemUserIdMatchAiChatUserIdMap + ", membershipCacheMap="
				+ membershipCacheMap + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			AiChatOptionService tmp = new Gson().fromJson(jsonStr, AiChatOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("AI chat option loaded");
		} catch (Exception e) {
			log.error("AI chat option loading error: " + e.getLocalizedMessage());
		}
	}
}
