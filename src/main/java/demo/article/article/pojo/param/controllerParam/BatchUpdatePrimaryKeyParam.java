package demo.article.article.pojo.param.controllerParam;

import java.util.Date;

public class BatchUpdatePrimaryKeyParam {

	private Date startTime;
	private Date endTime;

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

	@Override
	public String toString() {
		return "BatchUpdatePrimaryKeyParam [startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
