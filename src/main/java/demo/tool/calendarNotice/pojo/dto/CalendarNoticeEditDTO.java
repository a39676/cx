package demo.tool.calendarNotice.pojo.dto;

import java.time.LocalDateTime;

import demo.tool.calendarNotice.pojo.type.CalendarNoticeOperatorType;

public class CalendarNoticeEditDTO {

	private Long id;
	/** {@link CalendarNoticeOperatorType} */
	private Integer operatorType;
	
	private LocalDateTime newNoticeTime;
	
	
	private LocalDateTime newPreNoticeTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public LocalDateTime getNewNoticeTime() {
		return newNoticeTime;
	}

	public void setNewNoticeTime(LocalDateTime newNoticeTime) {
		this.newNoticeTime = newNoticeTime;
	}

	public LocalDateTime getNewPreNoticeTime() {
		return newPreNoticeTime;
	}

	public void setNewPreNoticeTime(LocalDateTime newPreNoticeTime) {
		this.newPreNoticeTime = newPreNoticeTime;
	}

}
