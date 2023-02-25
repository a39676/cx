package demo.tool.calendarNotice.pojo.type;

public enum CalendarNoticeTaskType {
	
	FIND_AND_SEND_NOTICE("findAndSendNotice", 1),
	FIND_AND_SNED_STRONG_NOTICE("findAndSendStrongNotice", 2),
	SEND_TOMORROW_NOTICE_LIST("sendTomorrowNoticeList", 3),
	;
	
	private String name;
	private Integer code;
	
	CalendarNoticeTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CalendarNoticeTaskType getType(String typeName) {
		for(CalendarNoticeTaskType t : CalendarNoticeTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CalendarNoticeTaskType getType(Integer typeCode) {
		for(CalendarNoticeTaskType t : CalendarNoticeTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
