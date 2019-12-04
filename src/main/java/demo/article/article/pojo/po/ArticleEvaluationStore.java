package demo.article.article.pojo.po;

import java.util.Date;

public class ArticleEvaluationStore {
	private Long evaluationStoreId;

	private Long postObjectId;

	private Integer evaluationType;

	private Date createTime;

	private Date updateTime;

	private Integer evaluationCode;

	private Integer evaluationCount;

	public Long getEvaluationStoreId() {
		return evaluationStoreId;
	}

	public void setEvaluationStoreId(Long evaluationStoreId) {
		this.evaluationStoreId = evaluationStoreId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public void setEvaluationCode(Integer evaluationCode) {
		this.evaluationCode = evaluationCode;
	}

	public Integer getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(Integer evaluationCount) {
		this.evaluationCount = evaluationCount;
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
		return "ArticleEvaluationStore [evaluationStoreId=" + evaluationStoreId + ", postObjectId=" + postObjectId
				+ ", evaluationType=" + evaluationType + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", evaluationCode=" + evaluationCode + ", evaluationCount=" + evaluationCount + "]";
	}

}