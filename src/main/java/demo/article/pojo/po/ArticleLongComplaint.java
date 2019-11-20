package demo.article.pojo.po;

import java.time.LocalDateTime;

public class ArticleLongComplaint {
    private Long id;

	private Long articleId;

	private Long articleCreatorId;

	private Long complaintUserId;

	private String articleTitle;

	private String complaintReason;

	private LocalDateTime createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getComplaintUserId() {
		return complaintUserId;
	}

	public void setComplaintUserId(Long complaintUserId) {
		this.complaintUserId = complaintUserId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle == null ? null : articleTitle.trim();
	}

	public String getComplaintReason() {
		return complaintReason;
	}

	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason == null ? null : complaintReason.trim();
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

}