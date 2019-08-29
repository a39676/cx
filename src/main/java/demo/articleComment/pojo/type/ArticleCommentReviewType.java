package demo.articleComment.pojo.type;

public enum ArticleCommentReviewType {
	
	pass("pass", 0),
//	reject("reject", 1),
	delete("delete", 2),
	;
	
	private String reviewName;
	private Integer reviewCode;
	
	ArticleCommentReviewType(String reviewName, Integer reviewCode) {
		this.reviewName = reviewName;
		this.reviewCode = reviewCode;
	}
	

	public String getReviewName() {
		return reviewName;
	}

	public Integer getReviewCode() {
		return reviewCode;
	}

	public static ArticleCommentReviewType getType(String typeName) {
		for(ArticleCommentReviewType t : ArticleCommentReviewType.values()) {
			if(t.getReviewName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
}
