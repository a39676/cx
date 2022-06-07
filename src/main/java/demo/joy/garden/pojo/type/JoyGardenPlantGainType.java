package demo.joy.garden.pojo.type;

public enum JoyGardenPlantGainType {
	
	FLOWER("flower", "花卉", 1),
	FRUIT("fruit", "果实", 2),
	SEED("seed", "种子", 3),
	;
	
	private String name;
	private String cnName;
	private Integer code;
	
	JoyGardenPlantGainType(String name, String cnName, Integer code) {
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

	public static JoyGardenPlantGainType getType(String typeName) {
		for(JoyGardenPlantGainType t : JoyGardenPlantGainType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyGardenPlantGainType getType(Integer typeCode) {
		for(JoyGardenPlantGainType t : JoyGardenPlantGainType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
