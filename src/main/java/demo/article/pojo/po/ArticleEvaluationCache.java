package demo.article.pojo.po;

import java.util.Date;

public class ArticleEvaluationCache {
	private Long evaluationCacheId;

	private Long postObjectId;

	private Integer evaluationType;

	private Date createTime;

	private Long userId;

	private Integer evaluationCode;

	private Boolean isDelete;

	private Boolean wasStatistics;

	public Long getEvaluationCacheId() {
		return evaluationCacheId;
	}

	public void setEvaluationCacheId(Long evaluationCacheId) {
		this.evaluationCacheId = evaluationCacheId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public void setEvaluationCode(Integer evaluationCode) {
		this.evaluationCode = evaluationCode;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getWasStatistics() {
		return wasStatistics;
	}

	public void setWasStatistics(Boolean wasStatistics) {
		this.wasStatistics = wasStatistics;
	}

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evaluationType) {
		this.evaluationType = evaluationType;
	}

	public Long getPostObjectId() {
		return postObjectId;
	}

	public void setPostObjectId(Long postObjectId) {
		this.postObjectId = postObjectId;
	}

	@Override
	public String toString() {
		return "ArticleEvaluationCache [evaluationCacheId=" + evaluationCacheId + ", postObjectId=" + postObjectId
				+ ", evaluationType=" + evaluationType + ", createTime=" + createTime + ", userId=" + userId
				+ ", evaluationCode=" + evaluationCode + ", isDelete=" + isDelete + ", wasStatistics=" + wasStatistics
				+ "]";
	}

}