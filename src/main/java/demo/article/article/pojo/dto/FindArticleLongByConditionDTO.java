package demo.article.article.pojo.dto;

public class FindArticleLongByConditionDTO {

	private Long articleId;

	private Boolean isDelete;

	private Boolean isPass;

	private Boolean isEdited;

	private Boolean isReject;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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

	@Override
	public String toString() {
		return "FindArticleLongByConditionDTO [articleId=" + articleId + ", isDelete=" + isDelete + ", isPass=" + isPass
				+ ", isEdited=" + isEdited + ", isReject=" + isReject + "]";
	}

}
