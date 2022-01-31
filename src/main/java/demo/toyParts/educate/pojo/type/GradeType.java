package demo.toyParts.educate.pojo.type;

public enum GradeType {
	
	grade1_1("1年级_上学期", 1),
	grade1_2("1年级_下学期", 2),
	grade2_1("2年级_上学期", 3),
	grade2_2("2年级_下学期", 4),
	grade3_1("3年级_上学期", 5),
	grade3_2("3年级_下学期", 6),
	grade4_1("4年级_上学期", 7),
	grade4_2("4年级_下学期", 8),
	grade5_1("5年级_上学期", 9),
	grade5_2("5年级_下学期", 10),
	grade6_1("6年级_上学期", 11),
	grade6_2("6年级_下学期", 12),
	grade7_1("7年级_上学期", 13),
	grade7_2("7年级_下学期", 14),
	grade8_1("8年级_上学期", 15),
	grade8_2("8年级_下学期", 16),
	grade9_1("9年级_上学期", 17),
	grade9_2("9年级_下学期", 18),
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
