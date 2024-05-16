package demo.ai.aiChat.service.impl;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.ai.aiChat.pojo.dto.AiChatUserMembershipDetailDTO;
import demo.common.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AiChatOptionService extends CommonService {

	private String chatStorePrefixPath;
	private String chatFromApiStorePrefixPath;
	private String extendDetailStorePrefixPath;
	private String payResultStorePrefixPath;
	private String userExtraJsonDetailStorePrefixPath;
	private Integer chatHistorySaveCountingLimit = 100;
	private Integer dailySignUpBonus;
	private String sensitiveWordsPathStr;
	private String forbiddenWordsPathStr;
	private Integer sensitiveWordsTriggerInMinutes;
	private Integer sensitiveWordsTriggerMaxCount;
	private List<AiChatUserMembershipDetailDTO> membershipLDetails;
	private Map<String, String> promptOfActAs = new HashMap<>();
	private Integer inputMaxLength;
	private Integer bonusForNewUser;
	private Integer chatHistoryCountLimitForFreeUser;
	private Integer maxCountOfApiKey = 5;
	private Integer maxCountOfapiKeyOperations = 10;
	private Integer maxBonusForDailySignUp;

	private Set<String> sensitiveWords = new HashSet<>();
	private Set<String> forbiddenWords = new HashSet<>();

	public String getChatStorePrefixPath() {
		return chatStorePrefixPath;
	}

	public void setChatStorePrefixPath(String chatStorePrefixPath) {
		this.chatStorePrefixPath = chatStorePrefixPath;
	}

	public String getChatFromApiStorePrefixPath() {
		return chatFromApiStorePrefixPath;
	}

	public void setChatFromApiStorePrefixPath(String chatFromApiStorePrefixPath) {
		this.chatFromApiStorePrefixPath = chatFromApiStorePrefixPath;
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

	public String getUserExtraJsonDetailStorePrefixPath() {
		return userExtraJsonDetailStorePrefixPath;
	}

	public void setUserExtraJsonDetailStorePrefixPath(String userExtraJsonDetailStorePrefixPath) {
		this.userExtraJsonDetailStorePrefixPath = userExtraJsonDetailStorePrefixPath;
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

	public String getForbiddenWordsPathStr() {
		return forbiddenWordsPathStr;
	}

	public void setForbiddenWordsPathStr(String forbiddenWordsPathStr) {
		this.forbiddenWordsPathStr = forbiddenWordsPathStr;
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

	public Set<String> getForbiddenWords() {
		return forbiddenWords;
	}

	public void setForbiddenWords(Set<String> forbiddenWords) {
		this.forbiddenWords = forbiddenWords;
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

	public Integer getMaxBonusForDailySignUp() {
		return maxBonusForDailySignUp;
	}

	public void setMaxBonusForDailySignUp(Integer maxBonusForDailySignUp) {
		this.maxBonusForDailySignUp = maxBonusForDailySignUp;
	}

	@Override
	public String toString() {
		return "AiChatOptionService [chatStorePrefixPath=" + chatStorePrefixPath + ", chatFromApiStorePrefixPath="
				+ chatFromApiStorePrefixPath + ", extendDetailStorePrefixPath=" + extendDetailStorePrefixPath
				+ ", payResultStorePrefixPath=" + payResultStorePrefixPath + ", userExtraJsonDetailStorePrefixPath="
				+ userExtraJsonDetailStorePrefixPath + ", chatHistorySaveCountingLimit=" + chatHistorySaveCountingLimit
				+ ", dailySignUpBonus=" + dailySignUpBonus + ", sensitiveWordsPathStr=" + sensitiveWordsPathStr
				+ ", forbiddenWordsPathStr=" + forbiddenWordsPathStr + ", sensitiveWordsTriggerInMinutes="
				+ sensitiveWordsTriggerInMinutes + ", sensitiveWordsTriggerMaxCount=" + sensitiveWordsTriggerMaxCount
				+ ", membershipLDetails=" + membershipLDetails + ", promptOfActAs=" + promptOfActAs
				+ ", inputMaxLength=" + inputMaxLength + ", bonusForNewUser=" + bonusForNewUser
				+ ", chatHistoryCountLimitForFreeUser=" + chatHistoryCountLimitForFreeUser + ", maxCountOfApiKey="
				+ maxCountOfApiKey + ", maxCountOfapiKeyOperations=" + maxCountOfapiKeyOperations
				+ ", maxBonusForDailySignUp=" + maxBonusForDailySignUp + ", sensitiveWords=" + sensitiveWords
				+ ", forbiddenWords=" + forbiddenWords + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.AI_CHAT);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.AI_CHAT);
			AiChatOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);

			if (StringUtils.isNotBlank(sensitiveWordsPathStr)) {
				log.error("Loading sensitive words");
				Path path = Paths.get(sensitiveWordsPathStr);
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null) {
					sensitiveWords.add(currentLine);
				}
				reader.close();
			} else {
				log.error("Can NOT find sensitive words");
			}

			if (StringUtils.isNotBlank(forbiddenWordsPathStr)) {
				log.error("Loading forbidden words");
				Path path = Paths.get(forbiddenWordsPathStr);
				BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
				String currentLine = null;
				while ((currentLine = reader.readLine()) != null) {
					forbiddenWords.add(currentLine);
				}
				reader.close();
			} else {
				log.error("Can NOT find forbidden words");
			}

			log.error("AI chat option loaded");
		} catch (Exception e) {
			log.error("AI chat option loading error: " + e.getLocalizedMessage());
		}
	}
}
