package demo.article.articleComment.pojo.dto;

import java.time.LocalDateTime;

public class FindCommentPageDTO {

	private Long id;

	private Long userId;

	private Long articleId;

	private LocalDateTime startTime;

	private Long replyOf;

	private Boolean isDelete;

	private Boolean isPass;

	private Boolean isReject;

	private Integer limit = 5;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Long getReplyOf() {
		return replyOf;
	}

	public void setReplyOf(Long replyOf) {
		this.replyOf = replyOf;
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

	public Boolean getIsReject() {
		return isReject;
	}

	public void setIsReject(Boolean isReject) {
		this.isReject = isReject;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "FindCommentPageDTO [id=" + id + ", userId=" + userId + ", articleId=" + articleId + ", startTime="
				+ startTime + ", replyOf=" + replyOf + ", isDelete=" + isDelete + ", isPass=" + isPass + ", isReject="
				+ isReject + ", limit=" + limit + "]";
	}

}
