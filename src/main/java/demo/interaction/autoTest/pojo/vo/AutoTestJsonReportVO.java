package demo.interaction.autoTest.pojo.vo;

import java.time.LocalDateTime;
import java.util.List;

public class AutoTestJsonReportVO {

	private Long id;
	private String title;

	private List<AutoTestJsonReportLineVO> contentLines;

	private LocalDateTime createTime;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private String createTimeStr;
	private String startTimeStr;
	private String endTimeStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<AutoTestJsonReportLineVO> getContentLines() {
		return contentLines;
	}

	public void setContentLines(List<AutoTestJsonReportLineVO> contentLines) {
		this.contentLines = contentLines;
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

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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

	@Override
	public String toString() {
		return "AutoTestJsonReportVO [id=" + id + ", title=" + title + ", contentLines=" + contentLines
				+ ", createTime=" + createTime + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createTimeStr=" + createTimeStr + ", startTimeStr=" + startTimeStr + ", endTimeStr=" + endTimeStr
				+ "]";
	}

}
