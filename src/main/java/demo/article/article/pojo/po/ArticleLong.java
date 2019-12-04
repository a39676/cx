package demo.article.article.pojo.po;

import java.util.Date;

public class ArticleLong {
	private Long articleId;

	private Long channelId;

	private Long userId;

	private String articleTitle;

	private String path;

	private Date createTime;

	private Date editTime;

	private Long editOf;

	private Integer editCount;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle == null ? null : articleTitle.trim();
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

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public Long getEditOf() {
		return editOf;
	}

	public void setEditOf(Long editOf) {
		this.editOf = editOf;
	}

	public Integer getEditCount() {
		return editCount;
	}

	public void setEditCount(Integer editCount) {
		this.editCount = editCount;
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

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Override
	public String toString() {
		return "ArticleLong [articleId=" + articleId + ", channelId=" + channelId + ", userId=" + userId
				+ ", articleTitle=" + articleTitle + ", path=" + path + ", createTime=" + createTime + ", editTime="
				+ editTime + ", editOf=" + editOf + ", editCount=" + editCount + ", isDelete=" + isDelete + ", isPass="
				+ isPass + ", isEdited=" + isEdited + ", isReject=" + isReject + "]";
	}

}