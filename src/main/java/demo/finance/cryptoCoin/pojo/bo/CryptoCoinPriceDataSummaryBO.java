package demo.finance.cryptoCoin.pojo.bo;

import java.time.LocalDateTime;

public class CryptoCoinPriceDataSummaryBO {

	private Integer coinTypeCode;
	private Integer currencyCode;

	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Double start;
	private Double end;
	private Double high;
	private Double low;

	public Integer getCoinTypeCode() {
		return coinTypeCode;
	}

	public void setCoinTypeCode(Integer coinTypeCode) {
		this.coinTypeCode = coinTypeCode;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Double getStart() {
		return start;
	}

	public void setStart(Double start) {
		this.start = start;
	}

	public Double getEnd() {
		return end;
	}

	public void setEnd(Double end) {
		this.end = end;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	@Override
	public String toString() {
		return "CryptoCoinPriceDataSummaryBO [coinTypeCode=" + coinTypeCode + ", currencyCode=" + currencyCode
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", start=" + start + ", end=" + end + ", high="
				+ high + ", low=" + low + "]";
	}
}
