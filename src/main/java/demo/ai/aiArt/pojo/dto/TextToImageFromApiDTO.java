package demo.ai.aiArt.pojo.dto;

import ai.aiArt.pojo.dto.TextToImageDTO;

public class TextToImageFromApiDTO extends TextToImageDTO {

	private String apiKey;
	private String callBack;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	@Override
	public String toString() {
		return "TextToImageFromApiDTO [apiKey=" + apiKey + ", callBack=" + callBack + "]";
	}

}
