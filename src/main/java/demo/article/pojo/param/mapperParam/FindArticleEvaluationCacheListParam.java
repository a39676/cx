package demo.article.pojo.param.mapperParam;

import java.util.Date;

public class FindArticleEvaluationCacheListParam {

	private Date startTime;
	private Date endTime;
	private Boolean wasStatistics = false;
	private Integer limit;

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

	public Boolean getWasStatistics() {
		return wasStatistics;
	}

	public void setWasStatistics(Boolean wasStatistics) {
		this.wasStatistics = wasStatistics;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "FindArticleEvaluationCacheListParam [startTime=" + startTime + ", endTime=" + endTime
				+ ", wasStatistics=" + wasStatistics + ", limit=" + limit + "]";
	}

}