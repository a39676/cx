package demo.article.pojo.type;

public enum ArticleEvaluationType {
	
	articleLongEvaluation("articleLongEvaluation", 1),
	articleCommentEvaluation("articleCommentEvaluation", 2),
	;
	
	private String name;
	private Integer code;
	
	ArticleEvaluationType(String evaluationName, Integer evaluationCode) {
		this.name = evaluationName;
		this.code = evaluationCode;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ArticleEvaluationType getType(String typeName) {
		for(ArticleEvaluationType t : ArticleEvaluationType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleEvaluationType getType(Integer typeCode) {
		for(ArticleEvaluationType t : ArticleEvaluationType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
