package demo.tool.calendarNotice.pojo.dto;

public class EditCalendarNoticeDTO extends AddCalendarNoticeDTO {

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EditCalendarNoticeDTO [id=" + id + ", getNoticeContent()=" + getNoticeContent() + ", getNoticeTime()="
				+ getNoticeTime() + ", getValidTime()=" + getValidTime() + ", getNeedRepeat()=" + getNeedRepeat()
				+ ", getIsLunarNotice()=" + getIsLunarNotice() + ", getLunarNoticeTime()=" + getLunarNoticeTime()
				+ ", getRepeatTimeUnit()=" + getRepeatTimeUnit() + ", getRepeatTimeRange()=" + getRepeatTimeRange()
				+ ", getPreNoticeRepeatTimeUnit()=" + getPreNoticeRepeatTimeUnit() + ", getPreNoticeRepeatTimeRange()="
				+ getPreNoticeRepeatTimeRange() + ", getPreNoticeCount()=" + getPreNoticeCount() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
