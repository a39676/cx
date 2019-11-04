package demo.article.pojo.param.controllerParam;

public class FindArticleLongByArticleSummaryPrivateKeyParam {

	private Long articleId;

	private String privateKey;

	private Boolean isDelete;

	private Boolean isPass;

	private Boolean isEdited;

	private Boolean isReject;

	private Long userId;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FindArticleLongByArticleSummaryPrivateKeyParam [articleId=" + articleId + ", privateKey=" + privateKey
				+ ", isDelete=" + isDelete + ", isPass=" + isPass + ", isEdited=" + isEdited + ", isReject=" + isReject
				+ ", userId=" + userId + "]";
	}

}
