package demo.aiArt.pojo.type;

public enum AiArtSamplerType {
	
	Eul("eul", 1),
	DPM_2M_KARRAS("DPM++ 2M Karras", 2),
	;
	
	private String name;
	private Integer code;
	
	AiArtSamplerType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static AiArtSamplerType getType(String typeName) {
		for(AiArtSamplerType t : AiArtSamplerType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static AiArtSamplerType getType(Integer typeCode) {
		for(AiArtSamplerType t : AiArtSamplerType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
