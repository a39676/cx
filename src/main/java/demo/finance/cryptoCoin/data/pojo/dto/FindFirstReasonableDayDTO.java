package demo.finance.cryptoCoin.data.pojo.dto;

import java.time.LocalDateTime;

public class FindFirstReasonableDayDTO {

	private Long coinTypeId;

	private LocalDateTime startTime;

	public Long getCoinTypeId() {
		return coinTypeId;
	}

	public void setCoinTypeId(Long coinTypeId) {
		this.coinTypeId = coinTypeId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "FindFirstReasonableDayDTO [coinTypeId=" + coinTypeId + ", startTime=" + startTime + "]";
	}

}
