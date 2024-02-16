package demo.tool.educate.pojo.type;

public enum MatchGradeType {
	
	PAST_GRADE("PastGrade", 0),
	CURRENT_GRADE("CurrentGrade", 1),
	FUTURE_GRADE("FutureGrade", 2),
	
	;
	
	private String name;
	private Integer code;
	
	MatchGradeType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static MatchGradeType getType(String typeName) {
		for(MatchGradeType t : MatchGradeType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static MatchGradeType getType(Integer typeCode) {
		for(MatchGradeType t : MatchGradeType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
