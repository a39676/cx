package demo.article.article.pojo.type;

public enum ArticleReviewType {
	
	pass("pass", 0),
	reject("reject", 1),
	delete("delete", 2),
	;
	
	private String reviewName;
	private Integer reviewCode;
	
	ArticleReviewType(String reviewName, Integer reviewCode) {
		this.reviewName = reviewName;
		this.reviewCode = reviewCode;
	}
	

	public String getReviewName() {
		return reviewName;
	}

	public Integer getReviewCode() {
		return reviewCode;
	}

	public static ArticleReviewType getType(String typeName) {
		for(ArticleReviewType t : ArticleReviewType.values()) {
			if(t.getReviewName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
}
