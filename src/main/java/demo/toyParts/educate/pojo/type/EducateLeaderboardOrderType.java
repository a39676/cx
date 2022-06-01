package demo.toyParts.educate.pojo.type;

public enum EducateLeaderboardOrderType {

	ORDER_BY_POINT("orderByPoint", "按积分排名", 1),
	ORDER_BY_SCORE("orderByScore", "按成绩排名", 2), 
	;

	private String name;
	private String cnName;
	private Integer code;

	EducateLeaderboardOrderType(String name, String cnName, Integer code) {
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

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public Integer getCode() {
		return code;
	}

	public static EducateLeaderboardOrderType getType(String typeName) {
		for (EducateLeaderboardOrderType t : EducateLeaderboardOrderType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static EducateLeaderboardOrderType getType(Integer typeCode) {
		for (EducateLeaderboardOrderType t : EducateLeaderboardOrderType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
