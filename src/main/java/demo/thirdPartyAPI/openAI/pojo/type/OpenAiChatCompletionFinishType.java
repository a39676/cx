package demo.thirdPartyAPI.openAI.pojo.type;

public enum OpenAiChatCompletionFinishType {
	
	STOP("stop", 1),
	LENGTH("length", 2),
	;
	
	private String name;
	private Integer code;
	
	OpenAiChatCompletionFinishType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static OpenAiChatCompletionFinishType getType(String typeName) {
		for(OpenAiChatCompletionFinishType t : OpenAiChatCompletionFinishType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static OpenAiChatCompletionFinishType getType(Integer typeCode) {
		for(OpenAiChatCompletionFinishType t : OpenAiChatCompletionFinishType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
