package demo.toyParts.educate.pojo.dto;

import java.util.List;

public class MathExerciesDTO extends ExerciesCommonDTO {

	private List<MathQuestionBaseDTO> questionList;

	public List<MathQuestionBaseDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<MathQuestionBaseDTO> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "MathExerciesDTO [questionList=" + questionList + "]";
	}

}
