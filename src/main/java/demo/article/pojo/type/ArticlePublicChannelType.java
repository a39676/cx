package demo.article.pojo.type;

/** 仅记录必存在的公共频道. */
public enum ArticlePublicChannelType {
	
	c1("公地", 1L),
	;
	
	private String channelName;
	private Long channelCode;
	
	ArticlePublicChannelType(String channelName, Long channelCode) {
		this.channelName = channelName;
		this.channelCode = channelCode;
	}
	

	public String getChannelName() {
		return channelName;
	}

	public Long getChannelCode() {
		return channelCode;
	}

	public static ArticlePublicChannelType getType(String typeName) {
		for(ArticlePublicChannelType t : ArticlePublicChannelType.values()) {
			if(t.getChannelName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticlePublicChannelType getType(Long typeCode) {
		for(ArticlePublicChannelType t : ArticlePublicChannelType.values()) {
			if(t.getChannelCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
