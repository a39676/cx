package demo.finance.cryptoCoin.data.pojo.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinBigMoveSummaryBySymbolBO implements Comparable<CryptoCoinBigMoveSummaryBySymbolBO> {

	private LocalDateTime startTime;
	private String startTimeStr;
	private Integer counting = 0;
	private Integer risingCounting = 0;
	private Integer fallingCounting = 0;
	private BigDecimal summaryRatio = BigDecimal.ZERO;

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public Integer getCounting() {
		return counting;
	}

	public void setCounting(Integer counting) {
		this.counting = counting;
	}

	public Integer getRisingCounting() {
		return risingCounting;
	}

	public void setRisingCounting(Integer risingCounting) {
		this.risingCounting = risingCounting;
	}

	public Integer getFallingCounting() {
		return fallingCounting;
	}

	public void setFallingCounting(Integer fallingCounting) {
		this.fallingCounting = fallingCounting;
	}

	public BigDecimal getSummaryRatio() {
		return summaryRatio;
	}

	public void setSummaryRatio(BigDecimal summaryRatio) {
		this.summaryRatio = summaryRatio;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigMoveSummaryBySymbolBO [startTime=" + startTime + ", startTimeStr=" + startTimeStr
				+ ", counting=" + counting + ", risingCounting=" + risingCounting + ", fallingCounting="
				+ fallingCounting + ", summaryRatio=" + summaryRatio + "]";
	}

	@Override
	public int compareTo(CryptoCoinBigMoveSummaryBySymbolBO o) {
		return compareStartTime(o, this);
	}

	private int compareStartTime(CryptoCoinBigMoveSummaryBySymbolBO o, CryptoCoinBigMoveSummaryBySymbolBO t) {
		if (o.getStartTime() == null || t.getStartTime() == null) {
			if (o.getStartTime() == null && t.getStartTime() == null) {
				return 0;
			} else if (o.getStartTime() == null) {
				return 1;
			} else if (t.getStartTime() == null) {
				return -1;
			} else {
				return 0;
			}
		} else {
			if (t.getStartTime().isAfter(o.getStartTime())) {
				return 1;
			} else if (t.getStartTime().isBefore(o.getStartTime())) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
