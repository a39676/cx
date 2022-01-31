package demo.toyParts.educate.pojo.dto;

import java.util.List;

import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;

public class MathExerciesDTO {

	private Long exerciesID;

	private Long userId;

	private GradeType gradeType;

	private ExerciesSubjectType subjectType;

	private List<MathQuestionBaseDTO> questionList;

	public Long getExerciesID() {
		return exerciesID;
	}

	public void setExerciesID(Long exerciesID) {
		this.exerciesID = exerciesID;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public ExerciesSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciesSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	@Override
	public String toString() {
		return "MathExerciesDTO [exerciesID=" + exerciesID + ", userId=" + userId + ", gradeType=" + gradeType
				+ ", subjectType=" + subjectType + ", questionList=" + questionList + "]";
	}

}
