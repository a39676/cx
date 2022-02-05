package demo.toyParts.educate.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class AnswerElementDTO {

	private Integer questionNumber;

	private List<String> answer = new ArrayList<>();

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "AnswerSubDTO [questionNumber=" + questionNumber + ", answer=" + answer + "]";
	}

}
