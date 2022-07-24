package demo.toyParts.educate.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.type.ExerciseSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;

public class ExerciseBuildResult extends CommonResult {

	private String exercisePK;

	private ExerciseSubjectType subjectType;

	private GradeType gradeType;

	private List<MathQuestionBaseDTO> questionList;

	public String getExercisePK() {
		return exercisePK;
	}

	public void setExercisePK(String exercisePK) {
		this.exercisePK = exercisePK;
	}

	public ExerciseSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciseSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public GradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(GradeType gradeType) {
		this.gradeType = gradeType;
	}

	public List<MathQuestionBaseDTO> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<MathQuestionBaseDTO> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "ExerciseBuildResult [exercisePK=" + exercisePK + ", subjectType=" + subjectType + ", gradeType="
				+ gradeType + ", questionList=" + questionList + "]";
	}

}
