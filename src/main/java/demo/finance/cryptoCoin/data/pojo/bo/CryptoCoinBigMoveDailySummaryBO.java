package demo.finance.cryptoCoin.data.pojo.bo;

import java.time.LocalDateTime;

public class CryptoCoinBigMoveDailySummaryBO implements Comparable<CryptoCoinBigMoveDailySummaryBO> {

	private LocalDateTime startTime;
	private String startTimeStr;
	private Integer total = 0;
	private Integer mainCounting = 0;
	private Integer mainSummaryCounting = 0;
	private Integer mainRisingCounting = 0;
	private Integer mainFallingCounting = 0;
	private Integer otherCounting = 0;
	private Integer otherSummaryCounting = 0;
	private Integer otherRisingCounting = 0;
	private Integer otherFallingCounting = 0;

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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getMainCounting() {
		return mainCounting;
	}

	public void setMainCounting(Integer mainCounting) {
		this.mainCounting = mainCounting;
	}

	public Integer getMainSummaryCounting() {
		return mainSummaryCounting;
	}

	public void setMainSummaryCounting(Integer mainSummaryCounting) {
		this.mainSummaryCounting = mainSummaryCounting;
	}

	public Integer getMainRisingCounting() {
		return mainRisingCounting;
	}

	public void setMainRisingCounting(Integer mainRisingCounting) {
		this.mainRisingCounting = mainRisingCounting;
	}

	public Integer getMainFallingCounting() {
		return mainFallingCounting;
	}

	public void setMainFallingCounting(Integer mainFallingCounting) {
		this.mainFallingCounting = mainFallingCounting;
	}

	public Integer getOtherCounting() {
		return otherCounting;
	}

	public void setOtherCounting(Integer otherCounting) {
		this.otherCounting = otherCounting;
	}

	public Integer getOtherSummaryCounting() {
		return otherSummaryCounting;
	}

	public void setOtherSummaryCounting(Integer otherSummaryCounting) {
		this.otherSummaryCounting = otherSummaryCounting;
	}

	public Integer getOtherRisingCounting() {
		return otherRisingCounting;
	}

	public void setOtherRisingCounting(Integer otherRisingCounting) {
		this.otherRisingCounting = otherRisingCounting;
	}

	public Integer getOtherFallingCounting() {
		return otherFallingCounting;
	}

	public void setOtherFallingCounting(Integer otherFallingCounting) {
		this.otherFallingCounting = otherFallingCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigMoveDailySummaryBO [startTime=" + startTime + ", startTimeStr=" + startTimeStr + ", total="
				+ total + ", mainCounting=" + mainCounting + ", mainSummaryCounting=" + mainSummaryCounting
				+ ", mainRisingCounting=" + mainRisingCounting + ", mainFallingCounting=" + mainFallingCounting
				+ ", otherCounting=" + otherCounting + ", otherSummaryCounting=" + otherSummaryCounting
				+ ", otherRisingCounting=" + otherRisingCounting + ", otherFallingCounting=" + otherFallingCounting
				+ "]";
	}

	@Override
	public int compareTo(CryptoCoinBigMoveDailySummaryBO o) {
		return compareStartTime(o, this);
	}

	private int compareStartTime(CryptoCoinBigMoveDailySummaryBO o, CryptoCoinBigMoveDailySummaryBO t) {
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
