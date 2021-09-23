package demo.base.system.pojo.result;

public enum HostnameType {
	
	zhang3("zhang3", 5),
	
	dtro("dtro", 6),
	;
	
	private String name;
	private Integer code;
	
	HostnameType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static HostnameType getType(String typeName) {
		for(HostnameType t : HostnameType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static HostnameType getTypeCustom(String typeName) {
		if(typeName.contains("zhang3")) {
			return zhang3;
		}
		return null;
	}
	
	public static HostnameType getType(Integer typeCode) {
		for(HostnameType t : HostnameType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
