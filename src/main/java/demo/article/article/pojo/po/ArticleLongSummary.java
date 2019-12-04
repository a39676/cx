package demo.article.article.pojo.po;

import java.util.Date;

public class ArticleLongSummary {
	private Long articleId;

	private Long userId;

	private String articleTitle;

	private String path;

	private Date createTime;

	private String privateKey;

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

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String toString() {
		return "ArticleLongSummary [articleId=" + articleId + ", userId=" + userId + ", articleTitle=" + articleTitle
				+ ", path=" + path + ", createTime=" + createTime + ", privateKey=" + privateKey + "]";
	}

}