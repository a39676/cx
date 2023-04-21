package demo.ai.aiArt.pojo.dto;

import ai.aiArt.pojo.dto.TextToImageFromDTO;

public class TextToImageFromApiDTO extends TextToImageFromDTO {

	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "TextToImageFromApiDTO [apiKey=" + apiKey + "]";
	}

}
