package demo.finance.cryptoCoin.data.pojo.bo;

import java.time.LocalDateTime;

public class CryptoCoinBigMoveDailySummaryBO implements Comparable<CryptoCoinBigMoveDailySummaryBO> {

	private LocalDateTime datetime;
	private Integer total = 0;
	private Integer binanceCounting = 0;
	private Integer gateIoCounting = 0;

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
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

	public Integer getGateIoCounting() {
		return gateIoCounting;
	}

	public void setGateIoCounting(Integer gateIoCounting) {
		this.gateIoCounting = gateIoCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigMoveDailySummaryBO [datetime=" + datetime + ", total=" + total + ", binanceCounting="
				+ binanceCounting + ", gateIoCounting=" + gateIoCounting + "]";
	}

	@Override
	public int compareTo(CryptoCoinBigMoveDailySummaryBO o) {
		return compare(o, this);
	}

	private int compare(CryptoCoinBigMoveDailySummaryBO o, CryptoCoinBigMoveDailySummaryBO t) {
		if (o.getDatetime() == null && t.getDatetime() == null) {
			return 0;
		} else if (o.getDatetime() == null) {
			return -1;
		} else if (t.getDatetime() == null) {
			return 1;
		}
		if (o.getDatetime().isBefore(t.getDatetime())) {
			return -1;
		} else if (o.getDatetime().isEqual(t.getDatetime())) {
			return 0;
		} else {
			return 1;
		}
	}

}
