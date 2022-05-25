package demo.joy.garden.pojo.type;

public enum JoyGardenPlantType {
	
	FLOWER("flower", "花卉", 1),
	AQUATIC("aquatic", "水生植物", 2),
	SHRUB("shrub", "灌木", 3),
	;
	
	private String name;
	private String cnName;
	private Integer code;
	
	JoyGardenPlantType(String name, String cnName, Integer code) {
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

	public static JoyGardenPlantType getType(String typeName) {
		for(JoyGardenPlantType t : JoyGardenPlantType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static JoyGardenPlantType getType(Integer typeCode) {
		for(JoyGardenPlantType t : JoyGardenPlantType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
