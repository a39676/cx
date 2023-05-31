package demo.ai.aiArt.pojo.dto;

import ai.aiArt.pojo.dto.ImageToImageDTO;

public class ImageToImageFromApiDTO extends ImageToImageDTO {

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
