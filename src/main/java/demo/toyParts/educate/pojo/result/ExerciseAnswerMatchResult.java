package demo.toyParts.educate.pojo.result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.toyParts.educate.pojo.type.MatchGradeType;

public class ExerciseAnswerMatchResult extends CommonResult {

	private BigDecimal totalScore = BigDecimal.ZERO;

	private BigDecimal points = BigDecimal.ZERO;

	/** {@link MatchGradeType} */
	private MatchGradeType matchGradeType = MatchGradeType.CURRENT_GRADE;

	private List<Integer> wrongNumberList = new ArrayList<>();

	private Integer questionListSize = 0;

	private Map<Integer, List<String>> answerMap = new HashMap<>();

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public MatchGradeType getMatchGradeType() {
		return matchGradeType;
	}

	public void setMatchGradeType(MatchGradeType matchGradeType) {
		this.matchGradeType = matchGradeType;
	}

	public List<Integer> getWrongNumberList() {
		return wrongNumberList;
	}

	public void setWrongNumberList(List<Integer> wrongNumberList) {
		this.wrongNumberList = wrongNumberList;
	}

	public Integer getQuestionListSize() {
		return questionListSize;
	}

	public void setQuestionListSize(Integer questionListSize) {
		this.questionListSize = questionListSize;
	}

	public Map<Integer, List<String>> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<Integer, List<String>> answerMap) {
		this.answerMap = answerMap;
	}

	@Override
	public String toString() {
		return "ExerciseAnswerMatchResult [totalScore=" + totalScore + ", points=" + points + ", matchGradeType="
				+ matchGradeType + ", wrongNumberList=" + wrongNumberList + ", answerListSize=" + questionListSize
				+ ", answerMap=" + answerMap + "]";
	}

}
