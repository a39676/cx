package demo.image.pojo.dto;

import java.time.LocalDateTime;

public class FindOldAutoTestImageOnCloudinaryDTO {

	private Long tagId;
	private LocalDateTime endTime;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "FindOldAutoTestImageOnCloudinaryDTO [tagId=" + tagId + ", endTime=" + endTime + ", getTagId()="
				+ getTagId() + ", getEndTime()=" + getEndTime() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
