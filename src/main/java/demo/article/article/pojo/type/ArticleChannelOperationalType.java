package demo.article.article.pojo.type;

public enum ArticleChannelOperationalType {
	
	add("add", 0),
	modify("modify", 1),
	updateDelete("updateDelete", 2),
	;
	
	private String name;
	private Integer code;
	
	ArticleChannelOperationalType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ArticleChannelOperationalType getType(String typeName) {
		for(ArticleChannelOperationalType t : ArticleChannelOperationalType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleChannelOperationalType getType(Integer typeCode) {
		for(ArticleChannelOperationalType t : ArticleChannelOperationalType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
