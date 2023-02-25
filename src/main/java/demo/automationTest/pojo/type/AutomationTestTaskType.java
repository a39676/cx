package demo.automationTest.pojo.type;

public enum AutomationTestTaskType {
	
	DELETE_OLD_DATA("deleteOldData", 1),
	SEND_TEST_EVENT_TO_RUN("sendTestEventToRun", 2),
	HANDLE_LONG_WAITING_EVENT("handleLongWaitingEvent", 3),
	;
	
	private String name;
	private Integer code;
	
	AutomationTestTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static AutomationTestTaskType getType(String typeName) {
		for(AutomationTestTaskType t : AutomationTestTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static AutomationTestTaskType getType(Integer typeCode) {
		for(AutomationTestTaskType t : AutomationTestTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
