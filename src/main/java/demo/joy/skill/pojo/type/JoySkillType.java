package demo.joy.skill.pojo.type;

public enum JoySkillType {
	
	firstMiracle("firstMiracle", 0),
	normalA("normalA", 1),
	
	;
	
	private String name;
	private Integer code;
	
	JoySkillType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static JoySkillType getType(String typeName) {
		for(JoySkillType t : JoySkillType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoySkillType getType(Integer typeCode) {
		for(JoySkillType t : JoySkillType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
