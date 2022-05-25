package demo.toyParts.educate.pojo.type;

public enum MathBaseSymbolType {

	addition("addition", "+", 1), subtraction("subtraction", "-", 2), multiplication("multiplication", "*", 3),
	division("division", "/", 4),;

	private String name;
	private String codeSymbol;
	private Integer code;

	MathBaseSymbolType(String name, String codeSymbol, Integer code) {
		this.name = name;
		this.codeSymbol = codeSymbol;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCodeSymbol() {
		return codeSymbol;
	}

	public Integer getCode() {
		return code;
	}

	public static MathBaseSymbolType getType(String typeName) {
		for (MathBaseSymbolType t : MathBaseSymbolType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static MathBaseSymbolType getType(Integer typeCode) {
		for (MathBaseSymbolType t : MathBaseSymbolType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
