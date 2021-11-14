package demo.tool.calendarNotice.pojo.dto;

import java.time.LocalDateTime;

import auxiliaryCommon.pojo.type.TimeUnitType;

public class AddCalendarNoticeDTO {

	private String noticeContent;
	private LocalDateTime noticeTime;
	private LocalDateTime validTime;

	private Boolean needRepeat = false;
	private Boolean isLunarNotice = false;

	/** {@link TimeUnitType} */
	private Integer repeatTimeUnit;
	private Integer repeatTimeRange;

	/** {@link TimeUnitType} */
	private Integer preNoticeRepeatTimeUnit;
	private Integer preNoticeRepeatTimeRange;
	private Integer preNoticeCount;

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public LocalDateTime getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(LocalDateTime noticeTime) {
		this.noticeTime = noticeTime;
	}

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	public Boolean getNeedRepeat() {
		return needRepeat;
	}

	public void setNeedRepeat(Boolean needRepeat) {
		this.needRepeat = needRepeat;
	}

	public Boolean getIsLunarNotice() {
		return isLunarNotice;
	}

	public void setIsLunarNotice(Boolean isLunarNotice) {
		this.isLunarNotice = isLunarNotice;
	}

	public Integer getRepeatTimeUnit() {
		return repeatTimeUnit;
	}

	public void setRepeatTimeUnit(Integer repeatTimeUnit) {
		this.repeatTimeUnit = repeatTimeUnit;
	}

	public Integer getRepeatTimeRange() {
		return repeatTimeRange;
	}

	public void setRepeatTimeRange(Integer repeatTimeRange) {
		this.repeatTimeRange = repeatTimeRange;
	}

	public Integer getPreNoticeRepeatTimeUnit() {
		return preNoticeRepeatTimeUnit;
	}

	public void setPreNoticeRepeatTimeUnit(Integer preNoticeRepeatTimeUnit) {
		this.preNoticeRepeatTimeUnit = preNoticeRepeatTimeUnit;
	}

	public Integer getPreNoticeRepeatTimeRange() {
		return preNoticeRepeatTimeRange;
	}

	public void setPreNoticeRepeatTimeRange(Integer preNoticeRepeatTimeRange) {
		this.preNoticeRepeatTimeRange = preNoticeRepeatTimeRange;
	}

	public Integer getPreNoticeCount() {
		return preNoticeCount;
	}

	public void setPreNoticeCount(Integer preNoticeCount) {
		this.preNoticeCount = preNoticeCount;
	}

	@Override
	public String toString() {
		return "AddCalendarNoticeDTO [noticeContent=" + noticeContent + ", noticeTime=" + noticeTime + ", validTime="
				+ validTime + ", needRepeat=" + needRepeat + ", isLunarNotice=" + isLunarNotice + ", repeatTimeUnit="
				+ repeatTimeUnit + ", repeatTimeRange=" + repeatTimeRange + ", preNoticeRepeatTimeUnit="
				+ preNoticeRepeatTimeUnit + ", preNoticeRepeatTimeRange=" + preNoticeRepeatTimeRange
				+ ", preNoticeCount=" + preNoticeCount + "]";
	}

}
