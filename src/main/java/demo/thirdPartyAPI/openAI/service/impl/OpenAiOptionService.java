package demo.thirdPartyAPI.openAI.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class OpenAiOptionService {

	@Value("${optionFilePath.openAI}")
	private String optionFilePath;
	private String apiKey = "";
	private String orgId = "";
	private Integer maxTokens = 500;
	private String chatStorePrefixPath;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Integer getMaxTokens() {
		return maxTokens;
	}

	public void setMaxTokens(Integer maxTokens) {
		this.maxTokens = maxTokens;
	}

	public String getChatStorePrefixPath() {
		return chatStorePrefixPath;
	}

	public void setChatStorePrefixPath(String chatStorePrefixPath) {
		this.chatStorePrefixPath = chatStorePrefixPath;
	}

	@Override
	public String toString() {
		return "OpenAiOptionService [apiKey=" + apiKey + ", orgId=" + orgId + ", maxTokens=" + maxTokens
				+ ", chatStorePrefixPath=" + chatStorePrefixPath + "]";
	}

}
