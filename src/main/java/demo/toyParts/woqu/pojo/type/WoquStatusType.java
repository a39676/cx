package demo.toyParts.woqu.pojo.type;

public enum WoquStatusType {
	
	inUse("启用", 1),
	;
	
	private String name;
	private Integer code;
	
	WoquStatusType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static WoquStatusType getType(String typeName) {
		for(WoquStatusType t : WoquStatusType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static WoquStatusType getType(Integer typeCode) {
		for(WoquStatusType t : WoquStatusType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
