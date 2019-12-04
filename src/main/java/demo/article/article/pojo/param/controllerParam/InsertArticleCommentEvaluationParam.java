package demo.article.article.pojo.param.controllerParam;

public class InsertArticleCommentEvaluationParam {

	private Long commentId;

	private Integer evaluationType;

	private Integer evaluationCode;

	


	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evaluationType) {
		this.evaluationType = evaluationType;
	}

	public Integer getEvaluationCode() {
		return evaluationCode;
	}

	public void setEvaluationCode(Integer evaluationCode) {
		this.evaluationCode = evaluationCode;
	}

	@Override
	public String toString() {
		return "InsertArticleCommentEvaluationParam [commentId=" + commentId + ", evaluationType=" + evaluationType
				+ ", evaluationCode=" + evaluationCode + "]";
	}

}
