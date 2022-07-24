package demo.toyParts.educate.pojo.type;

public enum ExerciseSubjectType {

	chinese("chinese", "语文", 1), 
	math("math", "数学", 2), 
	english("english", "英语", 3),
	;

	private String name;
	private String cnName;
	private Integer code;

	ExerciseSubjectType(String name, String cnName, Integer code) {
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

	public static ExerciseSubjectType getType(String typeName) {
		for (ExerciseSubjectType t : ExerciseSubjectType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static ExerciseSubjectType getType(Integer typeCode) {
		for (ExerciseSubjectType t : ExerciseSubjectType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
