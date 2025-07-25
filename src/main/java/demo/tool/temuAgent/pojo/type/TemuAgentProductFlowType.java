package demo.tool.temuAgent.pojo.type;

public enum TemuAgentProductFlowType {
	
	STOCKING("stocking", 1),
	INTERNATIONAL_STOCKING("internationalStocking", 2),
	SELLED("selled", 3),
	REPACKAGE("repackage", 4),
	;
	
	private String name;
	private Integer code;
	
	TemuAgentProductFlowType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static TemuAgentProductFlowType getType(String typeName) {
		for(TemuAgentProductFlowType t : TemuAgentProductFlowType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static TemuAgentProductFlowType getType(Integer typeCode) {
		for(TemuAgentProductFlowType t : TemuAgentProductFlowType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
