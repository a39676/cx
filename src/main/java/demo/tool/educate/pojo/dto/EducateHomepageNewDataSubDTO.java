package demo.tool.educate.pojo.dto;

import java.math.BigDecimal;

import demo.tool.educate.pojo.type.ExerciseSubjectType;
import demo.tool.educate.pojo.type.GradeType;

public class EducateHomepageNewDataSubDTO {

	private ExerciseSubjectType subjectType;
	private GradeType gradeType;
	private Long timeConsumInMinute;
	private String startTimeStr;
	private String endTimeStr;
	private BigDecimal score;

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

	public Long getTimeConsumInMinute() {
		return timeConsumInMinute;
	}

	public void setTimeConsumInMinute(Long timeConsumInMinute) {
		this.timeConsumInMinute = timeConsumInMinute;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "EducateHomepageNewDataSubDTO [subjectType=" + subjectType + ", gradeType=" + gradeType
				+ ", timeConsumInMinute=" + timeConsumInMinute + ", startTimeStr=" + startTimeStr + ", endTimeStr="
				+ endTimeStr + ", score=" + score + "]";
	}

}
