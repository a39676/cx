package demo.toyParts.educate.pojo.dto;

import demo.toyParts.educate.pojo.type.ExerciseSubjectType;

public class EducateHomepageSubjectDataSummaryDTO {

	private ExerciseSubjectType subjectType;

	private Double lastScore;

	private Integer exerciseCountToday;
	private Double totalScoreToday;
	private Double avgScoreToday;

	private Integer exerciseCountSevenDays;
	private Double totalScoreSevenDays;
	private Double avgScoreSevenDays;

	private Integer exerciseCountThirtyDays;
	private Double totalScoreThirtyDays;
	private Double avgScoreThirtyDays;

	public Integer getExerciseCountToday() {
		return exerciseCountToday;
	}

	public void setExerciseCountToday(Integer exerciseCountToday) {
		this.exerciseCountToday = exerciseCountToday;
	}

	public Double getTotalScoreToday() {
		return totalScoreToday;
	}

	public void setTotalScoreToday(Double totalScoreToday) {
		this.totalScoreToday = totalScoreToday;
	}

	public Double getAvgScoreToday() {
		return avgScoreToday;
	}

	public void setAvgScoreToday(Double avgScoreToday) {
		this.avgScoreToday = avgScoreToday;
	}

	public ExerciseSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciseSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public Double getLastScore() {
		return lastScore;
	}

	public void setLastScore(Double lastScore) {
		this.lastScore = lastScore;
	}

	public Integer getExerciseCountSevenDays() {
		return exerciseCountSevenDays;
	}

	public void setExerciseCountSevenDays(Integer exerciseCountSevenDays) {
		this.exerciseCountSevenDays = exerciseCountSevenDays;
	}

	public Double getTotalScoreSevenDays() {
		return totalScoreSevenDays;
	}

	public void setTotalScoreSevenDays(Double totalScoreSevenDays) {
		this.totalScoreSevenDays = totalScoreSevenDays;
	}

	public Double getAvgScoreSevenDays() {
		return avgScoreSevenDays;
	}

	public void setAvgScoreSevenDays(Double avgScoreSevenDays) {
		this.avgScoreSevenDays = avgScoreSevenDays;
	}

	public Integer getExerciseCountThirtyDays() {
		return exerciseCountThirtyDays;
	}

	public void setExerciseCountThirtyDays(Integer exerciseCountThirtyDays) {
		this.exerciseCountThirtyDays = exerciseCountThirtyDays;
	}

	public Double getTotalScoreThirtyDays() {
		return totalScoreThirtyDays;
	}

	public void setTotalScoreThirtyDays(Double totalScoreThirtyDays) {
		this.totalScoreThirtyDays = totalScoreThirtyDays;
	}

	public Double getAvgScoreThirtyDays() {
		return avgScoreThirtyDays;
	}

	public void setAvgScoreThirtyDays(Double avgScoreThirtyDays) {
		this.avgScoreThirtyDays = avgScoreThirtyDays;
	}

	@Override
	public String toString() {
		return "EducateHomepageSubjectDataSummaryDTO [subjectType=" + subjectType + ", lastScore=" + lastScore
				+ ", exerciseCountToday=" + exerciseCountToday + ", totalScoreToday=" + totalScoreToday
				+ ", avgScoreToday=" + avgScoreToday + ", exerciseCountSevenDays=" + exerciseCountSevenDays
				+ ", totalScoreSevenDays=" + totalScoreSevenDays + ", avgScoreSevenDays=" + avgScoreSevenDays
				+ ", exerciseCountThirtyDays=" + exerciseCountThirtyDays + ", totalScoreThirtyDays="
				+ totalScoreThirtyDays + ", avgScoreThirtyDays=" + avgScoreThirtyDays + "]";
	}

}
