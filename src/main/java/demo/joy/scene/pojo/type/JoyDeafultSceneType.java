package demo.joy.scene.pojo.type;

public enum JoyDeafultSceneType {
	
	newBorn("newBorn", 1L),
	home("home", 2L),
	;
	
	private String name;
	private Long code;
	
	JoyDeafultSceneType(String name, Long code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Long getCode() {
		return code;
	}

	public static JoyDeafultSceneType getType(String typeName) {
		for(JoyDeafultSceneType t : JoyDeafultSceneType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyDeafultSceneType getType(Long typeCode) {
		for(JoyDeafultSceneType t : JoyDeafultSceneType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
