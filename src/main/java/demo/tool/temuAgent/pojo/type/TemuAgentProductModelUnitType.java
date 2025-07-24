package demo.tool.temuAgent.pojo.type;

public enum TemuAgentProductModelUnitType {
	
	PC("pc", 1),
	SET("set", 2),
	;
	
	private String name;
	private Integer code;
	
	TemuAgentProductModelUnitType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static TemuAgentProductModelUnitType getType(String typeName) {
		for(TemuAgentProductModelUnitType t : TemuAgentProductModelUnitType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static TemuAgentProductModelUnitType getType(Integer typeCode) {
		for(TemuAgentProductModelUnitType t : TemuAgentProductModelUnitType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
