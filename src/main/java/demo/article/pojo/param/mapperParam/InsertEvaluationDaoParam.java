package demo.article.pojo.param.mapperParam;

public class InsertEvaluationDaoParam {

	private Long userId;

	private Long postObjectId;

	private Integer evaluationType;

	private Integer evaluationCode;

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
		return "InsertEvaluationDaoParam [userId=" + userId + ", postObjectId=" + postObjectId + ", evaluationType="
				+ evaluationType + ", evaluationCode=" + evaluationCode + "]";
	}

}
