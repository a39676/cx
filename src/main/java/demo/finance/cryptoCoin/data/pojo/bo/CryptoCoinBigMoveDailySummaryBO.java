package demo.finance.cryptoCoin.data.pojo.bo;

import java.time.LocalDateTime;

public class CryptoCoinBigMoveDailySummaryBO implements Comparable<CryptoCoinBigMoveDailySummaryBO> {

	private LocalDateTime startTime;
	private String startTimeStr;
	private Integer total = 0;
	private Integer binanceCounting = 0;
	private Integer binance1Counting = 0;

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

	public Integer getBinanceCounting() {
		return binanceCounting;
	}

	public void setBinanceCounting(Integer binanceCounting) {
		this.binanceCounting = binanceCounting;
	}

	public Integer getBinance1Counting() {
		return binance1Counting;
	}

	public void setBinance1Counting(Integer binance1Counting) {
		this.binance1Counting = binance1Counting;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigMoveDailySummaryBO [startTime=" + startTime + ", startTimeStr=" + startTimeStr + ", total="
				+ total + ", binanceCounting=" + binanceCounting + ", gateIoCounting=" + binance1Counting + "]";
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
