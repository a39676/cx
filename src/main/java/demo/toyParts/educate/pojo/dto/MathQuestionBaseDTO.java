package demo.toyParts.educate.pojo.dto;

import java.math.BigDecimal;

public class MathQuestionBaseDTO {

	private Integer questionNumber;

	private String expression;

	private BigDecimal standardAnswer;

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public BigDecimal getStandardAnswer() {
		return standardAnswer;
	}

	public void setStandardAnswer(BigDecimal standardAnswer) {
		this.standardAnswer = standardAnswer;
	}

	@Override
	public String toString() {
		return "MathQuestionBaseDTO [questionNumber=" + questionNumber + ", expression=" + expression
				+ ", standardAnswer=" + standardAnswer + "]";
	}

}
