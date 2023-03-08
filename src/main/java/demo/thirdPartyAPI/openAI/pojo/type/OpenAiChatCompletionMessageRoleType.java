package demo.thirdPartyAPI.openAI.pojo.type;

public enum OpenAiChatCompletionMessageRoleType {
	
	SYSTEM("system", 1),
	ASSISTANT("assistant", 2),
	USER("user", 3),
	;
	
	private String name;
	private Integer code;
	
	OpenAiChatCompletionMessageRoleType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static OpenAiChatCompletionMessageRoleType getType(String typeName) {
		for(OpenAiChatCompletionMessageRoleType t : OpenAiChatCompletionMessageRoleType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static OpenAiChatCompletionMessageRoleType getType(Integer typeCode) {
		for(OpenAiChatCompletionMessageRoleType t : OpenAiChatCompletionMessageRoleType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
