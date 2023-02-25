package demo.pmemo.pojo.type;

public enum UrgeNoticeTaskType {
	
	SEND_URGE_NOTICE("sendUrgeNotice", 1),
	;
	
	private String name;
	private Integer code;
	
	UrgeNoticeTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static UrgeNoticeTaskType getType(String typeName) {
		for(UrgeNoticeTaskType t : UrgeNoticeTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static UrgeNoticeTaskType getType(Integer typeCode) {
		for(UrgeNoticeTaskType t : UrgeNoticeTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
