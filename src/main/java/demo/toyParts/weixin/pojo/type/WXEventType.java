package demo.toyParts.weixin.pojo.type;

/** 微信事件类型 */
public enum WXEventType {
	
	/** 关注 */
	subscribe("关注", 1),
	;
	
	private String name;
	private Integer code;
	
	WXEventType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static WXEventType getType(String typeName) {
		for(WXEventType t : WXEventType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static WXEventType getType(Integer typeCode) {
		for(WXEventType t : WXEventType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
