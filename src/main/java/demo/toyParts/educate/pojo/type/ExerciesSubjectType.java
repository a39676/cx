package demo.toyParts.educate.pojo.type;

public enum ExerciesSubjectType {

	chinese("chinese", "语文", 1), 
	math("math", "数学", 2), 
	english("english", "英语", 3),
	;

	private String name;
	private String cnName;
	private Integer code;

	ExerciesSubjectType(String name, String cnName, Integer code) {
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

	public static ExerciesSubjectType getType(String typeName) {
		for (ExerciesSubjectType t : ExerciesSubjectType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static ExerciesSubjectType getType(Integer typeCode) {
		for (ExerciesSubjectType t : ExerciesSubjectType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
