package demo.finance.cryptoCoin.data.pojo.bo;

public class CryptoCoinBigMoveDailySummaryBO implements Comparable<CryptoCoinBigMoveDailySummaryBO> {

	private Integer dayGap = 0;
	private Integer total = 0;
	private Integer binanceCounting = 0;
	private Integer gateIoCounting = 0;

	public Integer getDayGap() {
		return dayGap;
	}

	public void setDayGap(Integer dayGap) {
		this.dayGap = dayGap;
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
		return "CryptoCoinBigMoveDailySummaryBO [dayGap=" + dayGap + ", total=" + total + ", binanceCounting="
				+ binanceCounting + ", gateIoCounting=" + gateIoCounting + "]";
	}

	@Override
	public int compareTo(CryptoCoinBigMoveDailySummaryBO o) {
		return compare(o, this);
	}

	private int compare(CryptoCoinBigMoveDailySummaryBO o, CryptoCoinBigMoveDailySummaryBO t) {
		if (o.getDayGap() == null && t.getDayGap() == null) {
			return 0;
		} else if (o.getDayGap() == null) {
			return -1;
		} else if (t.getDayGap() == null) {
			return 1;
		}
		if (o.getDayGap() < t.getDayGap()) {
			return -1;
		} else if (o.getDayGap().equals(t.getDayGap())) {
			return 0;
		} else {
			return 1;
		}
	}

}
