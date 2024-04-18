package demo.tool.textMessageForward.telegram.pojo.type;

public enum TelegramChatType {
	
	PRIVATE("private", 1),
	GROUP("group", 2),
	SUPER_GROUP("superGroup", 3),
	CHANNEL("channel", 4),
	;
	
	private String name;
	private Integer code;
	
	TelegramChatType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static TelegramChatType getType(String typeName) {
		for(TelegramChatType t : TelegramChatType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static TelegramChatType getType(Integer typeCode) {
		for(TelegramChatType t : TelegramChatType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
