package demo.base.system.pojo.result;

public enum HostnameType {
	
//	ea("ea", 1), /* 2020-05-27 不再续期 */
//	seek("seek", 2), /* 2020-05-27 即将过期 */
//	three("three", 3), /* 2020-05-27 考虑不再续期 */
//	haven("haven", 4), /* 2020-05-27 即将过期 */
	zhang3("zhang3", 5),
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
