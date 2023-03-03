package demo.thirdPartyAPI.openAI.pojo.type;

public enum OpenAiModelType {
	
	DAVINCI_003("text-davinci-003", 1),
	GPT_V_3_5("gpt-3.5-turbo", 2),
	;
	
	private String name;
	private Integer code;
	
	OpenAiModelType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static OpenAiModelType getType(String typeName) {
		for(OpenAiModelType t : OpenAiModelType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static OpenAiModelType getType(Integer typeCode) {
		for(OpenAiModelType t : OpenAiModelType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
