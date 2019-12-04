package demo.article.article.pojo.param.mapperParam;

public class UpdateArticleLongReviewStatuParam {

	private Long articleId;
	private boolean isPass;
	private boolean isDelete;
	private boolean isReject;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public boolean isReject() {
		return isReject;
	}

	public void setReject(boolean isReject) {
		this.isReject = isReject;
	}

	@Override
	public String toString() {
		return "UpdateArticleLongReviewStatuParam [articleId=" + articleId + ", isPass=" + isPass + ", isDelete="
				+ isDelete + ", isReject=" + isReject + "]";
	}

}
