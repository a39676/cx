package demo.articleComment.pojo.po;

import java.util.Date;

public class ArticleComment {
	private Long articleCommentId;

	private Long userId;

	private Long articleId;

	private String path;

	private Date createTime;

	private Long replyOf;

	private Boolean isDelete;

	private Boolean isPass;

	private Boolean isReject;

	private String privateKey;

	public Long getArticleCommentId() {
		return articleCommentId;
	}

	public void setArticleCommentId(Long articleCommentId) {
		this.articleCommentId = articleCommentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey == null ? null : privateKey.trim();
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "ArticleComment [articleCommentId=" + articleCommentId + ", userId=" + userId + ", articleId="
				+ articleId + ", path=" + path + ", createTime=" + createTime + ", replyOf=" + replyOf + ", isDelete="
				+ isDelete + ", isPass=" + isPass + ", isReject=" + isReject + ", privateKey=" + privateKey + "]";
	}

}