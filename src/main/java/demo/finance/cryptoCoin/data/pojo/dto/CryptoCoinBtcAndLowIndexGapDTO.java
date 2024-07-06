package demo.finance.cryptoCoin.data.pojo.dto;

import java.time.LocalDateTime;

public class CryptoCoinBtcAndLowIndexGapDTO {

	private Double gap;
	private LocalDateTime startTime;

	public Double getGap() {
		return gap;
	}

	public void setGap(Double gap) {
		this.gap = gap;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "CryptoCoinBtcAndLowIndexGapDTO [gap=" + gap + ", startTime=" + startTime + "]";
	}

}
