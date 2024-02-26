package demo.tool.educate.pojo.type;

public enum GradeType {
	
	GRADE_CUSTOM("自定义", 0),
	GRADE_1_1("1年级_上学期", 1),
	GRADE_1_2("1年级_下学期", 2),
	GRADE_2_1("2年级_上学期", 3),
	GRADE_2_2("2年级_下学期", 4),
	GRADE_3_1("3年级_上学期", 5),
	GRADE_3_2("3年级_下学期", 6),
	GRADE_4_1("4年级_上学期", 7),
	GRADE_4_2("4年级_下学期", 8),
	GRADE_5_1("5年级_上学期", 9),
	GRADE_5_2("5年级_下学期", 10),
	GRADE_6_1("6年级_上学期", 11),
	GRADE_6_2("6年级_下学期", 12),
	GRADE_7_1("7年级_上学期", 13),
	GRADE_7_2("7年级_下学期", 14),
	GRADE_8_1("8年级_上学期", 15),
	GRADE_8_2("8年级_下学期", 16),
	GRADE_9_1("9年级_上学期", 17),
	GRADE_9_2("9年级_下学期", 18),
	;
	
	private String name;
	private Integer code;
	
	GradeType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static GradeType getType(String typeName) {
		for(GradeType t : GradeType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static GradeType getType(Integer typeCode) {
		for(GradeType t : GradeType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
