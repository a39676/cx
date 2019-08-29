package demo.article.pojo.type;

public enum ArticleEvaluationType {
	
	articleLongEvaluation("articleLongEvaluation", 1),
	articleCommentEvaluation("articleCommentEvaluation", 2),
	;
	
	private String evaluationName;
	private Integer evaluationCode;
	
	ArticleEvaluationType(String evaluationName, Integer evaluationCode) {
		this.evaluationName = evaluationName;
		this.evaluationCode = evaluationCode;
	}
	

	public String getEvaluationName() {
		return evaluationName;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public static ArticleEvaluationType getType(String typeName) {
		for(ArticleEvaluationType t : ArticleEvaluationType.values()) {
			if(t.getEvaluationName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleEvaluationType getType(Integer typeCode) {
		for(ArticleEvaluationType t : ArticleEvaluationType.values()) {
			if(t.getEvaluationCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
