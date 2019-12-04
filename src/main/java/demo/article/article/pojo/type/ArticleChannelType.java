package demo.article.article.pojo.type;

public enum ArticleChannelType {
	
	publicChannel("public", 0),
	flashChannel("flashChanel", 1),
	privateChannel("privateChannel", 2),
	;
	
	private String channelTypeName;
	private Integer channelTypeCode;
	
	ArticleChannelType(String channelTypeName, Integer channelTypeCode) {
		this.channelTypeName = channelTypeName;
		this.channelTypeCode = channelTypeCode;
	}
	

	public String getName() {
		return channelTypeName;
	}

	public Integer getCode() {
		return channelTypeCode;
	}

	public static ArticleChannelType getType(String typeName) {
		for(ArticleChannelType t : ArticleChannelType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleChannelType getType(Integer typeCode) {
		for(ArticleChannelType t : ArticleChannelType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
