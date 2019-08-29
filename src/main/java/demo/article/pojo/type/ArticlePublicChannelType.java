package demo.article.pojo.type;

/** 仅记录必存在的公共频道. */
public enum ArticlePublicChannelType {
	
	c1("公地", 1L),
	c11("软广告", 11L),
	c12("硬广", 12L),
	c15("昆虫", 15L),
	c16("其他宠物", 16L),
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
