package demo.article.article.pojo.po;

import java.util.Date;

public class ArticleBurn {
	private Long articleId;

	private Integer readCount;

	private Integer readLimit;

	private Date validTime;

	private String readKey;

	private String burnKey;

	private Boolean isBurned;

	private Date createTime;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getReadLimit() {
		return readLimit;
	}

	public void setReadLimit(Integer readLimit) {
		this.readLimit = readLimit;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getReadKey() {
		return readKey;
	}

	public void setReadKey(String readKey) {
		this.readKey = readKey;
	}

	public String getBurnKey() {
		return burnKey;
	}

	public void setBurnKey(String burnKey) {
		this.burnKey = burnKey;
	}

	public Boolean getIsBurned() {
		return isBurned;
	}

	public void setIsBurned(Boolean isBurned) {
		this.isBurned = isBurned;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}