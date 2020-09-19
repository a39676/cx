package demo.tool.ocr.pojo.type;

public enum TessLanguageType {
	
	CHI_SIM("chi_sim"),
	CHI_TRA("chi_tra"),
	ENG("eng"),
	
	;
	
	private String name;
	
	TessLanguageType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public static TessLanguageType getType(String typeName) {
		for(TessLanguageType t : TessLanguageType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
}
