package demo.base.user.pojo.dto;

import java.time.LocalDateTime;

public class FindLastUserIpDTO {

	private LocalDateTime startTime = LocalDateTime.now().minusMonths(3);

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "FindLastUserIpDTO [startTime=" + startTime + "]";
	}

}
