package demo.toyParts.educate.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.educate.pojo.dto.MathQuestionBaseDTO;
import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;

public class ExerciesBuildResult extends CommonResult {

	private String exerciesPK;

	private ExerciesSubjectType subjectType;

	private GradeType gradeType;

	private List<MathQuestionBaseDTO> questionList;

	public String getExerciesPK() {
		return exerciesPK;
	}

	public void setExerciesPK(String exerciesPK) {
		this.exerciesPK = exerciesPK;
	}

	public ExerciesSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciesSubjectType subjectType) {
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
		return "ExerciesBuildResult [exerciesPK=" + exerciesPK + ", subjectType=" + subjectType + ", gradeType="
				+ gradeType + ", questionList=" + questionList + "]";
	}

}
