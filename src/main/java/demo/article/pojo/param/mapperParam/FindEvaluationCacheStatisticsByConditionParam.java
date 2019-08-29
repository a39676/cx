package demo.article.pojo.param.mapperParam;

import java.util.Date;

public class FindEvaluationCacheStatisticsByConditionParam {

	private Date startTime;

	private Date endTime;

	private Integer limit = 1000;

	private Integer evaluationType;

	public Integer getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Integer evaluationType) {
		this.evaluationType = evaluationType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "FindEvaluationCacheStatisticsByConditionParam [startTime=" + startTime + ", endTime=" + endTime
				+ ", limit=" + limit + ", evaluationType=" + evaluationType + "]";
	}

}
