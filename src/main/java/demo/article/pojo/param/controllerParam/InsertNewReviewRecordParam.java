package demo.article.pojo.param.controllerParam;

public class InsertNewReviewRecordParam {

	private Long articleId;

	private Long articleCreatorId;
	
	private Long articleReviewerId;
	
	private Integer reviewTypeId;
	
	private Long remarkId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getArticleCreatorId() {
		return articleCreatorId;
	}

	public void setArticleCreatorId(Long articleCreatorId) {
		this.articleCreatorId = articleCreatorId;
	}

	public Long getArticleReviewerId() {
		return articleReviewerId;
	}

	public void setArticleReviewerId(Long articleReviewerId) {
		this.articleReviewerId = articleReviewerId;
	}

	public Integer getReviewTypeId() {
		return reviewTypeId;
	}

	public void setReviewTypeId(Integer reviewTypeId) {
		this.reviewTypeId = reviewTypeId;
	}

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}

}
