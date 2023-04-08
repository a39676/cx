package demo.aiChat.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.aiChat.pojo.dto.AiChatUserMembershipDetailDTO;
import demo.common.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AiChatOptionService extends CommonService {

	@Value("${optionFilePath.aiChat}")
	private String optionFilePath;
	private String chatStorePrefixPath;
	private String extendDetailStorePrefixPath;
	private String payResultStorePrefixPath;
	private Integer chatHistorySaveCountingLimit = 100;
	private Integer dailySignUpBonus;
	private String sensitiveWordsPathStr;
	private Integer sensitiveWordsTriggerInMinutes;
	private Integer sensitiveWordsTriggerMaxCount;
	private List<AiChatUserMembershipDetailDTO> membershipLDetails;
	private Map<String, String> promptOfActAs = new HashMap<>();
	private Integer inputMaxLength;
	private Integer bonusForNewUser;
	private Integer chatHistoryCountLimitForFreeUser;
	private Integer maxCountOfApiKey = 5;
	private Integer maxCountOfapiKeyOperations = 10;

	private Set<String> sensitiveWords = new HashSet<>();

	public String getChatStorePrefixPath() {
		return chatStorePrefixPath;
	}

	public void setChatStorePrefixPath(String chatStorePrefixPath) {
		this.chatStorePrefixPath = chatStorePrefixPath;
	}

	public String getExtendDetailStorePrefixPath() {
		return extendDetailStorePrefixPath;
	}

	public void setExtendDetailStorePrefixPath(String extendDetailStorePrefixPath) {
		this.extendDetailStorePrefixPath = extendDetailStorePrefixPath;
	}

	public final String getPayResultStorePrefixPath() {
		return payResultStorePrefixPath;
	}

	public final void setPayResultStorePrefixPath(String payResultStorePrefixPath) {
		this.payResultStorePrefixPath = payResultStorePrefixPath;
	}

	public Integer getChatHistorySaveCountingLimit() {
		return chatHistorySaveCountingLimit;
	}

	public void setChatHistorySaveCountingLimit(Integer chatHistorySaveCountingLimit) {
		this.chatHistorySaveCountingLimit = chatHistorySaveCountingLimit;
	}

	public Integer getDailySignUpBonus() {
		return dailySignUpBonus;
	}

	public void setDailySignUpBonus(Integer dailySignUpBonus) {
		this.dailySignUpBonus = dailySignUpBonus;
	}

	public String getSensitiveWordsPathStr() {
		return sensitiveWordsPathStr;
	}

	public void setSensitiveWordsPathStr(String sensitiveWordsPathStr) {
		this.sensitiveWordsPathStr = sensitiveWordsPathStr;
	}

	public Integer getSensitiveWordsTriggerInMinutes() {
		return sensitiveWordsTriggerInMinutes;
	}

	public void setSensitiveWordsTriggerInMinutes(Integer sensitiveWordsTriggerInMinutes) {
		this.sensitiveWordsTriggerInMinutes = sensitiveWordsTriggerInMinutes;
	}

	public Integer getSensitiveWordsTriggerMaxCount() {
		return sensitiveWordsTriggerMaxCount;
	}

	public void setSensitiveWordsTriggerMaxCount(Integer sensitiveWordsTriggerMaxCount) {
		this.sensitiveWordsTriggerMaxCount = sensitiveWordsTriggerMaxCount;
	}

	public Set<String> getSensitiveWords() {
		return sensitiveWords;
	}

	public void setSensitiveWords(Set<String> sensitiveWords) {
		this.sensitiveWords = sensitiveWords;
	}

	public List<AiChatUserMembershipDetailDTO> getMembershipLDetails() {
		return membershipLDetails;
	}

	public void setMembershipLDetails(List<AiChatUserMembershipDetailDTO> membershipLDetails) {
		this.membershipLDetails = membershipLDetails;
	}

	public Map<String, String> getPromptOfActAs() {
		return promptOfActAs;
	}

	public void setPromptOfActAs(Map<String, String> promptOfActAs) {
		this.promptOfActAs = promptOfActAs;
	}

	public Integer getInputMaxLength() {
		return inputMaxLength;
	}

	public void setInputMaxLength(Integer inputMaxLength) {
		this.inputMaxLength = inputMaxLength;
	}

	public Integer getBonusForNewUser() {
		return bonusForNewUser;
	}

	public void setBonusForNewUser(Integer bonusForNewUser) {
		this.bonusForNewUser = bonusForNewUser;
	}

	public Integer getChatHistoryCountLimitForFreeUser() {
		return chatHistoryCountLimitForFreeUser;
	}

	public void setChatHistoryCountLimitForFreeUser(Integer chatHistoryCountLimitForFreeUser) {
		this.chatHistoryCountLimitForFreeUser = chatHistoryCountLimitForFreeUser;
	}

	public Integer getMaxCountOfApiKey() {
		return maxCountOfApiKey;
	}

	public void setMaxCountOfApiKey(Integer maxCountOfApiKey) {
		this.maxCountOfApiKey = maxCountOfApiKey;
	}

	public Integer getMaxCountOfapiKeyOperations() {
		return maxCountOfapiKeyOperations;
	}

	public void setMaxCountOfapiKeyOperations(Integer maxCountOfapiKeyOperations) {
		this.maxCountOfapiKeyOperations = maxCountOfapiKeyOperations;
	}

	@Override
	public String toString() {
		return "AiChatOptionService [chatStorePrefixPath=" + chatStorePrefixPath + ", extendDetailStorePrefixPath="
				+ extendDetailStorePrefixPath + ", payResultStorePrefixPath=" + payResultStorePrefixPath
				+ ", chatHistorySaveCountingLimit=" + chatHistorySaveCountingLimit + ", dailySignUpBonus="
				+ dailySignUpBonus + ", sensitiveWordsPathStr=" + sensitiveWordsPathStr
				+ ", sensitiveWordsTriggerInMinutes=" + sensitiveWordsTriggerInMinutes
				+ ", sensitiveWordsTriggerMaxCount=" + sensitiveWordsTriggerMaxCount + ", membershipLDetails="
				+ membershipLDetails + ", promptOfActAs=" + promptOfActAs + ", inputMaxLength=" + inputMaxLength
				+ ", bonusForNewUser=" + bonusForNewUser + ", chatHistoryCountLimitForFreeUser="
				+ chatHistoryCountLimitForFreeUser + ", maxCountOfApiKey=" + maxCountOfApiKey
				+ ", maxCountOfapiKeyOperations=" + maxCountOfapiKeyOperations + ", sensitiveWords=" + sensitiveWords
				+ "]";
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

			if (StringUtils.isNotBlank(sensitiveWordsPathStr)) {
				log.error("Loading sensitive words");
				Path path = Paths.get(sensitiveWordsPathStr);
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null) {
					sensitiveWords.add(currentLine);
				}
			} else {
				log.error("Can NOT find sensitive words");
			}

			log.error("AI chat option loaded");
		} catch (Exception e) {
			log.error("AI chat option loading error: " + e.getLocalizedMessage());
		}
	}
}
