package demo.aiArt.pojo.type;

public enum AiArtJobType {
	
	FAILED("failed", -1),
	WAITING("waiting", 0),
	SUCCESS("success", 1),
	;
	
	private String name;
	private Integer code;
	
	AiArtJobType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static AiArtJobType getType(String typeName) {
		for(AiArtJobType t : AiArtJobType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static AiArtJobType getType(Integer typeCode) {
		for(AiArtJobType t : AiArtJobType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
