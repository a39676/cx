package demo.joy.character.pojo.type;

public enum JoySceneOperationType {
	
	create("create", 1),
	edit("edit", 2),
	delete("delete", 3),
	restore("restore", 4),
	;
	
	private String name;
	private Integer code;
	
	JoySceneOperationType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static JoySceneOperationType getType(String typeName) {
		for(JoySceneOperationType t : JoySceneOperationType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoySceneOperationType getType(Integer typeCode) {
		for(JoySceneOperationType t : JoySceneOperationType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
