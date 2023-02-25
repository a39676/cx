package demo.tool.openAI.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("singleton")
@Service
public class OpenAiOptionService {

	@Value("${optionFilePath.openAI}")
	private String optionFilePath;

	private String apiKey = "";

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "ArticleOptionService [apiKey=" + apiKey + "]";
	}

}
