package demo.tool.calendarNotice.pojo.type;

public enum CalendarNoticeOperatorType {
	
	edit("edit", 1),
	dismiss("dismiss", 2),
	delete("delete", 3),
	;
	
	private String name;
	private Integer code;
	
	CalendarNoticeOperatorType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CalendarNoticeOperatorType getType(String typeName) {
		for(CalendarNoticeOperatorType t : CalendarNoticeOperatorType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CalendarNoticeOperatorType getType(Integer typeCode) {
		for(CalendarNoticeOperatorType t : CalendarNoticeOperatorType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
