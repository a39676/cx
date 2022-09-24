package demo.finance.currencyExchangeRate.data.pojo.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;

public class FilterDataResult extends CommonResult {
	private BigDecimal maxRate;
	private BigDecimal minRate;

	private LocalDateTime maxRateDateTime;
	private LocalDateTime minRateDateTime;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public BigDecimal getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(BigDecimal maxRate) {
		this.maxRate = maxRate;
	}

	public BigDecimal getMinRate() {
		return minRate;
	}

	public void setMinRate(BigDecimal minRate) {
		this.minRate = minRate;
	}

	public LocalDateTime getMaxRateDateTime() {
		return maxRateDateTime;
	}

	public void setMaxRateDateTime(LocalDateTime maxRateDateTime) {
		this.maxRateDateTime = maxRateDateTime;
	}

	public LocalDateTime getMinRateDateTime() {
		return minRateDateTime;
	}

	public void setMinRateDateTime(LocalDateTime minRateDateTime) {
		this.minRateDateTime = minRateDateTime;
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

	@Override
	public String toString() {
		return "FilterDataResult [maxRate=" + maxRate + ", minRate=" + minRate + ", maxRateDateTime=" + maxRateDateTime
				+ ", minRateDateTime=" + minRateDateTime + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
