package demo.tool.educate.pojo.dto;

import demo.tool.educate.pojo.type.ExerciseSubjectType;
import demo.tool.educate.pojo.type.GradeType;

public class ExerciseCommonDTO {

	private Long exerciseID;

	private Long userId;

	private GradeType gradeType;

	private ExerciseSubjectType subjectType;

	private String startTimeStr;

	public Long getExerciseID() {
		return exerciseID;
	}

	public void setExerciseID(Long exerciseID) {
		this.exerciseID = exerciseID;
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

	public ExerciseSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciseSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	@Override
	public String toString() {
		return "ExerciseCommonDTO [exerciseID=" + exerciseID + ", userId=" + userId + ", gradeType=" + gradeType
				+ ", subjectType=" + subjectType + ", startTimeStr=" + startTimeStr + "]";
	}

}
