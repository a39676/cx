package demo.article.pojo.bo;

public class ArticleEvaluationStatisticsByArticleIdBO {

	private Long postObjectId;

	private Integer evaluationCode;

	private Integer evaluationCount;

	private String userIds;

	public Long getPostObjectId() {
		return postObjectId;
	}

	public void setPostObjectId(Long postObjectId) {
		this.postObjectId = postObjectId;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public void setEvaluationCode(Integer evaluationCode) {
		this.evaluationCode = evaluationCode;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public Integer getEvaluationCount() {
		return evaluationCount;
	}

	public void setEvaluationCount(Integer evaluationCount) {
		this.evaluationCount = evaluationCount;
	}

	@Override
	public String toString() {
		return "ArticleEvaluationStatisticsByArticleIdBO [postObjectId=" + postObjectId + ", evaluationCode="
				+ evaluationCode + ", evaluationCount=" + evaluationCount + ", userIds=" + userIds + "]";
	}

}
