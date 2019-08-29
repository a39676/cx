package demo.article.pojo.type;

public enum ArticleEvaluationCodeType {
	
	up("↑↑", 1),
	down("↓↓", -1),
	;
	
	private String name;
	private Integer code;
	
	ArticleEvaluationCodeType(String evaluationName, Integer evaluationCode) {
		this.name = evaluationName;
		this.code = evaluationCode;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ArticleEvaluationCodeType getType(String typeName) {
		for(ArticleEvaluationCodeType t : ArticleEvaluationCodeType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleEvaluationCodeType getType(Integer typeCode) {
		for(ArticleEvaluationCodeType t : ArticleEvaluationCodeType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
