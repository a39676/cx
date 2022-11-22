package demo.base.task.pojo.type;

public enum TaskType {
	
	TEST("system", -1),
	
	SYSTEM("system", 1),
	ARTICLE("article", 2),
	AUTOMATION_TEST("automationTest", 3),
	BOOKMARK("bookmark", 4),
	CALENDAR_NOTICE("calendarNotice", 5),
	OLD_DATA_DELETE("oldDataDelete", 6),
	;
	
	private String name;
	private Integer code;
	
	TaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static TaskType getType(String typeName) {
		for(TaskType t : TaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static TaskType getType(Integer typeCode) {
		for(TaskType t : TaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
