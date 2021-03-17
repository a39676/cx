package demo.tool.ocr.pojo.type;

public enum TessLanguageType {
	
	ENG("eng", 0),
	CHI_SIM("chi_sim", 1),
	CHI_TRA("chi_tra", 2),
	
	;
	
	private String name;
	private Integer code;
	
	TessLanguageType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getCode() {
		return code;
	}

	public static TessLanguageType getType(String name) {
		for(TessLanguageType t : TessLanguageType.values()) {
			if(t.getName().equals(name)) {
				return t;
			}
		}
		return null;
	}
	
	public static TessLanguageType getType(Integer code) {
		for(TessLanguageType t : TessLanguageType.values()) {
			if(t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}
	
}
