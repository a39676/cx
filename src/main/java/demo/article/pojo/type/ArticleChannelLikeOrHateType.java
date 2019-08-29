package demo.article.pojo.type;

/** 用户对频道的喜好. */
public enum ArticleChannelLikeOrHateType {
	
	l1("like1", 1, 3),
	l2("like2", 2, 21),
	l3("like3", 3, 42),
	l4("like4", 4, 63),
	l5("like5", 5, 180),
	l6("like6", 6, 365),
	uncheck("uncheck", 0, 3),
	h1("hate1", -1, 3),
	h2("hate2", -2, 21),
	h3("hate3", -3, 42),
	h4("hate4", -4, 63),
	h5("hate5", -5, 180),
	h6("hate6", -6, 365),
	;
	
	private String level;
	private Integer code;
	private Integer delayDays;
	
	ArticleChannelLikeOrHateType(String level, Integer code, Integer delayDays) {
		this.level = level;
		this.code = code;
		this.delayDays = delayDays;
	}
	

	public String getLevel() {
		return level;
	}

	public Integer getCode() {
		return code;
	}
	
	public Integer getDelayDays() {
		return delayDays;
	}

	public static ArticleChannelLikeOrHateType getType(String level) {
		for(ArticleChannelLikeOrHateType t : ArticleChannelLikeOrHateType.values()) {
			if(t.getLevel().equals(level)) {
				return t;
			}
		}
		
		return null;
	}
	
	public static ArticleChannelLikeOrHateType getType(Integer code) {
		if(code == null) {
			return null;
		}
		if(code < -6) {
			return ArticleChannelLikeOrHateType.h6;
		} 
		if(code > 6){
			return ArticleChannelLikeOrHateType.l6;
		}
		for(ArticleChannelLikeOrHateType t : ArticleChannelLikeOrHateType.values()) {
			if(t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}
}
