package demo.article.article.pojo.type;

public enum ArticleTaskType {
	
	UPDATE_ARTICLE_HOT_EXPIRED("updateArticleHotExpired", 0),
	DELETE_ARTICLE_BY_VALID_SETTING("deleteArticleByValidSetting", 2),
	;
	
	private String channelTypeName;
	private Integer channelTypeCode;
	
	ArticleTaskType(String channelTypeName, Integer channelTypeCode) {
		this.channelTypeName = channelTypeName;
		this.channelTypeCode = channelTypeCode;
	}
	

	public String getName() {
		return channelTypeName;
	}

	public Integer getCode() {
		return channelTypeCode;
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
