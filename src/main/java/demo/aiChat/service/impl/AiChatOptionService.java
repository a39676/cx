package demo.aiChat.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.aiChat.pojo.dto.AiChatAesOptionDTO;
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
	private Map<String, AiChatAesOptionDTO> aesOptionMap;
	private List<AiChatUserMembershipLevelDetailDTO> membershipLevelDetail;

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

	public Map<String, AiChatAesOptionDTO> getAesOptionMap() {
		return aesOptionMap;
	}

	public void setAesOptionMap(Map<String, AiChatAesOptionDTO> aesOptionMap) {
		this.aesOptionMap = aesOptionMap;
	}

	public List<AiChatUserMembershipLevelDetailDTO> getMembershipLevelDetail() {
		return membershipLevelDetail;
	}

	public void setMembershipLevelDetail(List<AiChatUserMembershipLevelDetailDTO> membershipLevelDetail) {
		this.membershipLevelDetail = membershipLevelDetail;
	}

	@Override
	public String toString() {
		return "AiChatOptionService [chatStorePrefixPath=" + chatStorePrefixPath + ", chatHistorySaveCountingLimit="
				+ chatHistorySaveCountingLimit + ", aesOptionMap=" + aesOptionMap + ", membershipLevelDetail="
				+ membershipLevelDetail + "]";
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
