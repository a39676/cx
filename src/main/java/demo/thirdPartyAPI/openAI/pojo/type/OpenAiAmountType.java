package demo.thirdPartyAPI.openAI.pojo.type;

public enum OpenAiAmountType {
	
	BONUS("bonus", 1),
	RECHARGE("recharge", 2),
	;
	
	private String name;
	private Integer code;
	
	OpenAiAmountType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static OpenAiAmountType getType(String typeName) {
		for(OpenAiAmountType t : OpenAiAmountType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static OpenAiAmountType getType(Integer typeCode) {
		for(OpenAiAmountType t : OpenAiAmountType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
