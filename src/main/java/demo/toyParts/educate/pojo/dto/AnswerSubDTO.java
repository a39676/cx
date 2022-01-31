package demo.toyParts.educate.pojo.dto;

public class AnswerSubDTO {

	private Integer questionNumber;

	private String answer;

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "AnswerSubDTO [questionNumber=" + questionNumber + ", answer=" + answer + "]";
	}

}
