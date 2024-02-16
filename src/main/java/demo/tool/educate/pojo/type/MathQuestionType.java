package demo.tool.educate.pojo.type;

public enum MathQuestionType {

	CALCULATE("calculate", "计算题", 1), 
	FILL("fill", "填空题", 2), 
	WORD_PROBLEM("wordProblem", "应用题", 3),
	TRUE_OR_FALSE("trueOrFalse", "判断题", 4),
	;

	private String name;
	private String cnName;
	private Integer code;

	MathQuestionType(String name, String cnName, Integer code) {
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

	public static MathQuestionType getType(String typeName) {
		for (MathQuestionType t : MathQuestionType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static MathQuestionType getType(Integer typeCode) {
		for (MathQuestionType t : MathQuestionType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
