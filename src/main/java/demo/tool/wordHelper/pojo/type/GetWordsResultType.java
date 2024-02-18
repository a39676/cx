package demo.tool.wordHelper.pojo.type;

public enum GetWordsResultType {
	
	NO_WORDS("noWords", -1),
	NOT_ENOUGH_WORDS("notEnoughtWords", -2),
	;
	
	private String name;
	private Integer code;
	
	GetWordsResultType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static GetWordsResultType getType(String typeName) {
		for(GetWordsResultType t : GetWordsResultType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static GetWordsResultType getType(Integer typeCode) {
		for(GetWordsResultType t : GetWordsResultType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
