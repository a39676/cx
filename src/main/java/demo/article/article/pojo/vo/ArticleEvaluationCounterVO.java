package demo.article.article.pojo.vo;

public class ArticleEvaluationCounterVO {

	private Integer evaluationCode;

	private Integer evaluationCount;

	private String evaluationName;

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

	public String getEvaluationName() {
		return evaluationName;
	}

	public void setEvaluationName(String evaluationName) {
		this.evaluationName = evaluationName;
	}
	
	public ArticleEvaluationCounterVO addEvaluationCount(int count) {
		this.evaluationCount += count;
		return this;
	}

	@Override
	public String toString() {
		return "ArticleEvaluationCounterVO [evaluationCode=" + evaluationCode + ", evaluationCount=" + evaluationCount
				+ ", evaluationName=" + evaluationName + "]";
	}

}
