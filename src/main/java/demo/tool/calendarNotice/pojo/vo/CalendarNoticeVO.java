package demo.tool.calendarNotice.pojo.vo;

import java.time.LocalDateTime;

public class CalendarNoticeVO {

	private String pk;
	private String noticeContent;

	private Integer repeatTimeRange;
	private String repeatTimeUnitName;
	private Integer repeatTimeUnit;

	private String validTimeStr;
	private LocalDateTime validTime;

	private String noticeTimeStr;
	private LocalDateTime noticeTime;

	private boolean isLunarCalendar;
	private boolean strongNotice;

	private String preNoticePk;

	private Integer preNoticeRepeatTimeRange;
	private String preNoticeRepeatTimeUnitName;
	private Integer preNoticeRepeatTimeUnit;

	private Integer preNoticeRepeatCount;

	private String preNoticeTimeStr;
	private LocalDateTime preNoticeTime;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Integer getRepeatTimeRange() {
		return repeatTimeRange;
	}

	public void setRepeatTimeRange(Integer repeatTimeRange) {
		this.repeatTimeRange = repeatTimeRange;
	}

	public String getRepeatTimeUnitName() {
		return repeatTimeUnitName;
	}

	public void setRepeatTimeUnitName(String repeatTimeUnitName) {
		this.repeatTimeUnitName = repeatTimeUnitName;
	}

	public Integer getRepeatTimeUnit() {
		return repeatTimeUnit;
	}

	public void setRepeatTimeUnit(Integer repeatTimeUnit) {
		this.repeatTimeUnit = repeatTimeUnit;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	public String getNoticeTimeStr() {
		return noticeTimeStr;
	}

	public void setNoticeTimeStr(String noticeTimeStr) {
		this.noticeTimeStr = noticeTimeStr;
	}

	public LocalDateTime getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(LocalDateTime noticeTime) {
		this.noticeTime = noticeTime;
	}

	public boolean getIsLunarCalendar() {
		return isLunarCalendar;
	}

	public void setIsLunarCalendar(boolean isLunarCalendar) {
		this.isLunarCalendar = isLunarCalendar;
	}

	public boolean getStrongNotice() {
		return strongNotice;
	}

	public void setStrongNotice(boolean strongNotice) {
		this.strongNotice = strongNotice;
	}

	public String getPreNoticePk() {
		return preNoticePk;
	}

	public void setPreNoticePk(String preNoticePk) {
		this.preNoticePk = preNoticePk;
	}

	public Integer getPreNoticeRepeatTimeRange() {
		return preNoticeRepeatTimeRange;
	}

	public void setPreNoticeRepeatTimeRange(Integer preNoticeRepeatTimeRange) {
		this.preNoticeRepeatTimeRange = preNoticeRepeatTimeRange;
	}

	public String getPreNoticeRepeatTimeUnitName() {
		return preNoticeRepeatTimeUnitName;
	}

	public void setPreNoticeRepeatTimeUnitName(String preNoticeRepeatTimeUnitName) {
		this.preNoticeRepeatTimeUnitName = preNoticeRepeatTimeUnitName;
	}

	public Integer getPreNoticeRepeatTimeUnit() {
		return preNoticeRepeatTimeUnit;
	}

	public void setPreNoticeRepeatTimeUnit(Integer preNoticeRepeatTimeUnit) {
		this.preNoticeRepeatTimeUnit = preNoticeRepeatTimeUnit;
	}

	public Integer getPreNoticeRepeatCount() {
		return preNoticeRepeatCount;
	}

	public void setPreNoticeRepeatCount(Integer preNoticeRepeatCount) {
		this.preNoticeRepeatCount = preNoticeRepeatCount;
	}

	public String getPreNoticeTimeStr() {
		return preNoticeTimeStr;
	}

	public void setPreNoticeTimeStr(String preNoticeTimeStr) {
		this.preNoticeTimeStr = preNoticeTimeStr;
	}

	public LocalDateTime getPreNoticeTime() {
		return preNoticeTime;
	}

	public void setPreNoticeTime(LocalDateTime preNoticeTime) {
		this.preNoticeTime = preNoticeTime;
	}

	@Override
	public String toString() {
		return "CalendarNoticeVO [pk=" + pk + ", noticeContent=" + noticeContent + ", repeatTimeRange="
				+ repeatTimeRange + ", repeatTimeUnitName=" + repeatTimeUnitName + ", repeatTimeUnit=" + repeatTimeUnit
				+ ", validTimeStr=" + validTimeStr + ", validTime=" + validTime + ", noticeTimeStr=" + noticeTimeStr
				+ ", noticeTime=" + noticeTime + ", isLunarCalendar=" + isLunarCalendar + ", preNoticePk=" + preNoticePk
				+ ", preNoticeRepeatTimeRange=" + preNoticeRepeatTimeRange + ", preNoticeRepeatTimeUnitName="
				+ preNoticeRepeatTimeUnitName + ", preNoticeRepeatTimeUnit=" + preNoticeRepeatTimeUnit
				+ ", preNoticeRepeatCount=" + preNoticeRepeatCount + ", preNoticeTimeStr=" + preNoticeTimeStr
				+ ", preNoticeTime=" + preNoticeTime + "]";
	}

}
