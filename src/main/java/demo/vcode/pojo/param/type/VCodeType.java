package demo.vcode.pojo.param.type;

public enum VCodeType {
	
	forHR("forHR", 1),
	;
	
	private String codeTypeName;
	private Integer codeTypeValue;
	
	VCodeType(String reviewName, Integer reviewCode) {
		this.codeTypeName = reviewName;
		this.codeTypeValue = reviewCode;
	}
	
	public String getCodeTypeName() {
		return codeTypeName;
	}

	public Integer getCodeTypeValue() {
		return codeTypeValue;
	}

	public static VCodeType getType(String typeName) {
		for(VCodeType t : VCodeType.values()) {
			if(t.getCodeTypeName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static VCodeType getType(Integer typeName) {
		for(VCodeType t : VCodeType.values()) {
			if(t.getCodeTypeValue().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
}
