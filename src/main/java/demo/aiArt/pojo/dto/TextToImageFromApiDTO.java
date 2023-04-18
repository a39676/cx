package demo.aiArt.pojo.dto;

import ai.aiArt.pojo.dto.TextToImageFromDTO;

public class TextToImageFromApiDTO extends TextToImageFromDTO {

	private String ApiKey;

	public String getApiKey() {
		return ApiKey;
	}

	public void setApiKey(String apiKey) {
		ApiKey = apiKey;
	}

}
