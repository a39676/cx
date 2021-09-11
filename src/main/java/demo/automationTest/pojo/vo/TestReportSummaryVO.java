package demo.automationTest.pojo.vo;

import java.time.LocalDateTime;

public class TestReportSummaryVO {

	private String idStr;
	private String flowName;
	private LocalDateTime createTime;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "TestReportSummaryVO [idStr=" + idStr + ", flowName=" + flowName + ", createTime=" + createTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
