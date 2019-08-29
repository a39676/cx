package demo.article.pojo.vo;

public class ArticleLongSummaryVOV3 {
	private String articleTitle;

	private String nickName;

	private String headIamgeUrl;

	private String imgUrl;

	private String createDateString;

	private String createDateTimeString;

	private String privateKey;

	private Boolean hasCommentNotReview = false;

	private Integer viewCount = 0;

	private Integer commentCount = 0;

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadIamgeUrl() {
		return headIamgeUrl;
	}

	public void setHeadIamgeUrl(String headIamgeUrl) {
		this.headIamgeUrl = headIamgeUrl;
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

	@Override
	public String toString() {
		return "ArticleLongSummaryVOV3 [articleTitle=" + articleTitle + ", nickName=" + nickName + ", headIamgeUrl="
				+ headIamgeUrl + ", imgUrl=" + imgUrl + ", createDateString=" + createDateString
				+ ", createDateTimeString=" + createDateTimeString + ", privateKey=" + privateKey
				+ ", hasCommentNotReview=" + hasCommentNotReview + ", viewCount=" + viewCount + ", commentCount="
				+ commentCount + "]";
	}

}
