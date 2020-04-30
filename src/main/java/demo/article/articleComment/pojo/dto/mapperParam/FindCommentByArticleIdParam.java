package demo.article.articleComment.pojo.dto.mapperParam;

import java.time.LocalDateTime;

public class FindCommentByArticleIdParam {

	private Long articleId;
	private Boolean isDelete = false;
	private Boolean isPass = true;
	private Integer limit = 10;
	private LocalDateTime startTime;

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

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "FindCommentByArticleIdParam [articleId=" + articleId + ", isDelete=" + isDelete + ", isPass=" + isPass
				+ ", limit=" + limit + ", startTime=" + startTime + "]";
	}

}
