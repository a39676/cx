package demo.toyParts.educate.pojo.result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliaryCommon.pojo.result.CommonResult;

public class ExerciesAnswerMatchResult extends CommonResult {

	private BigDecimal totalScore = BigDecimal.ZERO;

	private Integer points = 0;

	private List<Integer> wrongNumberList = new ArrayList<>();

	private Map<Integer, List<String>> answerMap = new HashMap<>();

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public List<Integer> getWrongNumberList() {
		return wrongNumberList;
	}

	public void setWrongNumberList(List<Integer> wrongNumberList) {
		this.wrongNumberList = wrongNumberList;
	}

	public Map<Integer, List<String>> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<Integer, List<String>> answerMap) {
		this.answerMap = answerMap;
	}

	@Override
	public String toString() {
		return "ExerciesAnswerMatchResult [totalScore=" + totalScore + ", points=" + points + ", wrongNumberList="
				+ wrongNumberList + ", answerMap=" + answerMap + "]";
	}

}