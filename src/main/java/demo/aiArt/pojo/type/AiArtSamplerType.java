package demo.aiArt.pojo.type;

public enum AiArtSamplerType {
	
	Euler_A("Euler a", 1),
	Euler("Euler", 2),
	LMS("LMS", 3),
	Heun("Heun", 4),
	DPM2("DPM2", 5),
	DPM2_a("DPM2 a", 6),
	DPM_2S_a("DPM++ 2S a", 7),
	DPM_2M("DPM++ 2M", 8),
	DPM_SDE("DPM++ SDE", 9),
	DPM_fast("DPM fast", 10),
	DPM_adaptive("DPM adaptive", 11),
	LMS_Karras("LMS Karras", 12),
	DPM2_Karras("DPM2 Karras", 13),
	DPM2_a_Karras("DPM2 a Karras", 14),
	DPM_2S_a_Karras("DPM++ 2S a Karras", 15),
	DPM_2M_Karras("DPM++ 2M Karras", 16),
	DPM_SDE_Karras("DPM++ SDE Karras", 17),
	DDIM("DDIM", 18),
	PLMS("PLMS", 19),
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
