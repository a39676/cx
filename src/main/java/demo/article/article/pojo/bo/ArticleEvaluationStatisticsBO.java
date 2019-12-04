package demo.article.article.pojo.bo;

import java.util.List;

public class ArticleEvaluationStatisticsBO {

	private Integer evaluationType;

	private Long postObjectId;

	private Integer evaluationCode;

	private Integer evaluationCount;

	private String evaluationCacheIdStr;

	private List<Long> evaluationCacheId;

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evauationType) {
		this.evaluationType = evauationType;
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

	public List<Long> getEvaluationCacheId() {
		return evaluationCacheId;
	}

	public void setEvaluationCacheId(List<Long> evaluationCacheId) {
		this.evaluationCacheId = evaluationCacheId;
	}

	public String getEvaluationCacheIdStr() {
		return evaluationCacheIdStr;
	}

	public void setEvaluationCacheIdStr(String evaluationCacheIdStr) {
		this.evaluationCacheIdStr = evaluationCacheIdStr;
	}

	public Long getPostObjectId() {
		return postObjectId;
	}

	public void setPostObjectId(Long postObjectId) {
		this.postObjectId = postObjectId;
	}

	@Override
	public String toString() {
		return "ArticleEvaluationStatisticsBO [evauationType=" + evaluationType + ", postObjectId=" + postObjectId
				+ ", evaluationCode=" + evaluationCode + ", evaluationCount=" + evaluationCount
				+ ", evaluationCacheIdStr=" + evaluationCacheIdStr + ", evaluationCacheId=" + evaluationCacheId + "]";
	}

}
