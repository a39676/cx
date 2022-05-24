package demo.joy.garden.pojo.type;

public enum JoyGardenSubModuleType {
	
	
	PLANT_STAGE("plantStage", 1),
	
	;
	
	private String name;
	private Integer code;
	
	JoyGardenSubModuleType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static JoyGardenSubModuleType getType(String typeName) {
		for(JoyGardenSubModuleType t : JoyGardenSubModuleType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyGardenSubModuleType getType(Integer typeCode) {
		for(JoyGardenSubModuleType t : JoyGardenSubModuleType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
