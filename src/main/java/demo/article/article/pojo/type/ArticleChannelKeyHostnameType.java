package demo.article.article.pojo.type;

public enum ArticleChannelKeyHostnameType {
	
	pass("pass", 1),
	ban("ban", 2),
	;
	
	private String name;
	private Integer code;
	
	ArticleChannelKeyHostnameType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ArticleChannelKeyHostnameType getType(String typeName) {
		for(ArticleChannelKeyHostnameType t : ArticleChannelKeyHostnameType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ArticleChannelKeyHostnameType getType(Integer typeCode) {
		for(ArticleChannelKeyHostnameType t : ArticleChannelKeyHostnameType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
