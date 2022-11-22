package demo.article.article.pojo.type;

public enum ArticleTaskType {
	
	UPDATE_ARTICLE_HOT_EXPIRED("updateArticleHotExpired", 1),
	DELETE_ARTICLE_BY_VALID_SETTING("deleteArticleByValidSetting", 2),
	;
	
	private String name;
	private Integer code;
	
	ArticleTaskType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ArticleTaskType getType(String typeName) {
		for(ArticleTaskType t : ArticleTaskType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleTaskType getType(Integer typeCode) {
		for(ArticleTaskType t : ArticleTaskType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
