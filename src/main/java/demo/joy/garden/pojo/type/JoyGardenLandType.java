package demo.joy.garden.pojo.type;

public enum JoyGardenLandType {
	
	FIELD("field", "田地", 1),
	WETLAND("wetland", "湿地", 2),
	WOODLAND("woodland", "林地", 3),
	;
	
	private String name;
	private String cnName;
	private Integer code;
	
	JoyGardenLandType(String name, String cnName, Integer code) {
		this.name = name;
		this.cnName = cnName;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}
	
	public String getCnName() {
		return cnName;
	}

	public Integer getCode() {
		return code;
	}

	public static JoyGardenLandType getType(String typeName) {
		for(JoyGardenLandType t : JoyGardenLandType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyGardenLandType getType(Integer typeCode) {
		for(JoyGardenLandType t : JoyGardenLandType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
