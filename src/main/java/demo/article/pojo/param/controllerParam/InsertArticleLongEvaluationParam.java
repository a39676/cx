package demo.article.pojo.param.controllerParam;

public class InsertArticleLongEvaluationParam {

	private String pk;

	private Integer evaluationType;

	private Integer evaluationCode;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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
		return "InsertEvaluationParam [pk=" + pk + ", evaluationType=" + evaluationType + ", evaluationCode="
				+ evaluationCode + "]";
	}


}
