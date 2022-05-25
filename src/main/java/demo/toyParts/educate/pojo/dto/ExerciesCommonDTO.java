package demo.toyParts.educate.pojo.dto;

import demo.toyParts.educate.pojo.type.ExerciesSubjectType;
import demo.toyParts.educate.pojo.type.GradeType;

public class ExerciesCommonDTO {

	private Long exerciesID;

	private Long userId;

	private GradeType gradeType;

	private ExerciesSubjectType subjectType;

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

	public ExerciesSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciesSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	@Override
	public String toString() {
		return "ExerciesCommonDTO [exerciesID=" + exerciesID + ", userId=" + userId + ", gradeType=" + gradeType
				+ ", subjectType=" + subjectType + "]";
	}

}
