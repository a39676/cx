package demo.joy.common.pojo.type;

public enum JoyTaskType {
	
	CACHE_TO_DATABASE("cacheToDatabase", 1),
	;
	
	private String name;
	private Integer code;
	
	JoyTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static JoyTaskType getType(String typeName) {
		for(JoyTaskType t : JoyTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyTaskType getType(Integer typeCode) {
		for(JoyTaskType t : JoyTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
