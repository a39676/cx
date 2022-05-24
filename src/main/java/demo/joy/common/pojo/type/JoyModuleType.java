package demo.joy.common.pojo.type;

public enum JoyModuleType {
	
	GARDEN("garden", 1),
	CHARACTER("character", 2),
	ICON("icon", 3),
	NPC("npc", 4),
	SCENE("scene", 5),
	SKILL("skill", 6),
	;
	
	private String name;
	private Integer code;
	
	JoyModuleType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static JoyModuleType getType(String typeName) {
		for(JoyModuleType t : JoyModuleType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyModuleType getType(Integer typeCode) {
		for(JoyModuleType t : JoyModuleType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
