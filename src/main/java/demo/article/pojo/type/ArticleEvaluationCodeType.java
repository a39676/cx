package demo.article.pojo.type;

public enum ArticleEvaluationCodeType {
	
	up("↑↑", 1),
	down("↓↓", -1),
	;
	
	private String evaluationName;
	private Integer evaluationCode;
	
	ArticleEvaluationCodeType(String evaluationName, Integer evaluationCode) {
		this.evaluationName = evaluationName;
		this.evaluationCode = evaluationCode;
	}
	

	public String getEvaluationName() {
		return evaluationName;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public static ArticleEvaluationCodeType getType(String typeName) {
		for(ArticleEvaluationCodeType t : ArticleEvaluationCodeType.values()) {
			if(t.getEvaluationName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleEvaluationCodeType getType(Integer typeCode) {
		for(ArticleEvaluationCodeType t : ArticleEvaluationCodeType.values()) {
			if(t.getEvaluationCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
