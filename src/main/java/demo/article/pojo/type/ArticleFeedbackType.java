package demo.article.pojo.type;

public enum ArticleFeedbackType {
	
	normal("normal", 1),
	complaint("complaint", 2),
	;
	
	private String name;
	private Integer code;
	
	ArticleFeedbackType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ArticleFeedbackType getType(String typeName) {
		for(ArticleFeedbackType t : ArticleFeedbackType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleFeedbackType getType(Integer typeCode) {
		for(ArticleFeedbackType t : ArticleFeedbackType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
