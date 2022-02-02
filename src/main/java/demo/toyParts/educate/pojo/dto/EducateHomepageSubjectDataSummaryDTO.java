package demo.toyParts.educate.pojo.dto;

import demo.toyParts.educate.pojo.type.ExerciesSubjectType;

public class EducateHomepageSubjectDataSummaryDTO {

	private ExerciesSubjectType subjectType;

	private Double lastScore;

	private Integer exerciesCountSevenDays;
	private Double totalScoreSevenDays;
	private Double avgScoreSevenDays;

	private Integer exerciesCountThirtyDays;
	private Double totalScoreThirtyDays;
	private Double avgScoreThirtyDays;

	public ExerciesSubjectType getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(ExerciesSubjectType subjectType) {
		this.subjectType = subjectType;
	}

	public Double getLastScore() {
		return lastScore;
	}

	public void setLastScore(Double lastScore) {
		this.lastScore = lastScore;
	}

	public Integer getExerciesCountSevenDays() {
		return exerciesCountSevenDays;
	}

	public void setExerciesCountSevenDays(Integer exerciesCountSevenDays) {
		this.exerciesCountSevenDays = exerciesCountSevenDays;
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

	public Integer getExerciesCountThirtyDays() {
		return exerciesCountThirtyDays;
	}

	public void setExerciesCountThirtyDays(Integer exerciesCountThirtyDays) {
		this.exerciesCountThirtyDays = exerciesCountThirtyDays;
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
				+ ", exerciesCountSevenDays=" + exerciesCountSevenDays + ", totalScoreSevenDays=" + totalScoreSevenDays
				+ ", avgScoreSevenDays=" + avgScoreSevenDays + ", exerciesCountThirtyDays=" + exerciesCountThirtyDays
				+ ", totalScoreThirtyDays=" + totalScoreThirtyDays + ", avgScoreThirtyDays=" + avgScoreThirtyDays + "]";
	}

}
