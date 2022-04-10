package demo.article.article.pojo.vo;

public class ArticleLongSummaryVO {
	private String articleTitle;

	private String imgUrl;

	private String createDateString;

	private String createDateTimeString;

	private String privateKey;

	private Boolean hasCommentNotReview = false;

	private Integer viewCount = 0;

	private Long commentCount = 0L;

	private Boolean isHot = false;

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCreateDateString() {
		return createDateString;
	}

	public void setCreateDateString(String createDateString) {
		this.createDateString = createDateString;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Boolean getHasCommentNotReview() {
		return hasCommentNotReview;
	}

	public void setHasCommentNotReview(Boolean hasCommentNotReview) {
		this.hasCommentNotReview = hasCommentNotReview;
	}

	public String getCreateDateTimeString() {
		return createDateTimeString;
	}

	public void setCreateDateTimeString(String createDateTimeString) {
		this.createDateTimeString = createDateTimeString;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Override
	public String toString() {
		return "ArticleLongSummaryVOV3 [articleTitle=" + articleTitle + ", imgUrl=" + imgUrl + ", createDateString="
				+ createDateString + ", createDateTimeString=" + createDateTimeString + ", privateKey=" + privateKey
				+ ", hasCommentNotReview=" + hasCommentNotReview + ", viewCount=" + viewCount + ", commentCount="
				+ commentCount + ", isHot=" + isHot + "]";
	}

}
