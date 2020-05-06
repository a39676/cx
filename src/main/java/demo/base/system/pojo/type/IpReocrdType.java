package demo.base.system.pojo.type;

public enum IpReocrdType {
	
	deny("deny", 0),
	allow("allow", 1),
	;
	
	private String name;
	private Integer code;
	
	IpReocrdType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static IpReocrdType getType(String typeName) {
		for(IpReocrdType t : IpReocrdType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static IpReocrdType getType(Integer typeCode) {
		for(IpReocrdType t : IpReocrdType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
