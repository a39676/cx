package demo.tool.educate.pojo.dto;

import java.util.List;

public class MathExerciseDTO extends ExerciseCommonDTO {

	private List<MathQuestionBaseDTO> questionList;

	public List<MathQuestionBaseDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<MathQuestionBaseDTO> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "MathExerciseDTO [questionList=" + questionList + "]";
	}

}
