package demo.toyParts.educate.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class MathQuestionBaseDTO {

	private Integer questionNumber;

	private String expression;

	private List<String> standardAnswer = new ArrayList<>();

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

	public List<String> getStandardAnswer() {
		return standardAnswer;
	}

	public void setStandardAnswer(List<String> standardAnswer) {
		this.standardAnswer = standardAnswer;
	}
	
	public void addStandardAnswer(String standardAnswer) {
		 if(this.standardAnswer == null) {
			 this.standardAnswer = new ArrayList<>();
		 }
		 this.standardAnswer.add(standardAnswer);
	}

	@Override
	public String toString() {
		return "MathQuestionBaseDTO [questionNumber=" + questionNumber + ", expression=" + expression
				+ ", standardAnswer=" + standardAnswer + "]";
	}

}
