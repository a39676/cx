package demo.tool.pojo.dto;

import java.time.LocalDateTime;

public class GetVisitCountTotalDTO {

	private Boolean isDelete = false;
	private LocalDateTime startTime;

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "GetVisitCountTotalDTO [isDelete=" + isDelete + ", startTime=" + startTime + ", getIsDelete()="
				+ getIsDelete() + ", getStartTime()=" + getStartTime() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
