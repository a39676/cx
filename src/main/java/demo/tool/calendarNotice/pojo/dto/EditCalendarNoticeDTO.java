package demo.tool.calendarNotice.pojo.dto;

public class EditCalendarNoticeDTO extends AddCalendarNoticeDTO {

	private String pk;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "EditCalendarNoticeDTO [pk=" + pk + ", getNoticeContent()=" + getNoticeContent() + ", getNoticeTime()="
				+ getNoticeTime() + ", getValidTime()=" + getValidTime() + ", getIsLunarNotice()=" + getIsLunarNotice()
				+ ", getLunarNoticeTime()=" + getLunarNoticeTime() + ", getRepeatTimeUnit()=" + getRepeatTimeUnit()
				+ ", getRepeatTimeRange()=" + getRepeatTimeRange() + ", getPreNoticeRepeatTimeUnit()="
				+ getPreNoticeRepeatTimeUnit() + ", getPreNoticeRepeatTimeRange()=" + getPreNoticeRepeatTimeRange()
				+ ", getPreNoticeCount()=" + getPreNoticeCount() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
