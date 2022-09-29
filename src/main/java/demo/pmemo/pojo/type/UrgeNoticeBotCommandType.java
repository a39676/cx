package demo.pmemo.pojo.type;

public enum UrgeNoticeBotCommandType {
	
	ADD("/add", 1),
	DELETE("/delete", 2),
	SHOW("/show", 3),
	CLEAR("/clear", 4),
	;
	
	private String name;
	private Integer code;
	
	UrgeNoticeBotCommandType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static UrgeNoticeBotCommandType getType(String typeName) {
		for(UrgeNoticeBotCommandType t : UrgeNoticeBotCommandType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static UrgeNoticeBotCommandType getType(Integer typeCode) {
		for(UrgeNoticeBotCommandType t : UrgeNoticeBotCommandType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
