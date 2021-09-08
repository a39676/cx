package demo.finance.cryptoCoin.data.pojo.bo;

import java.time.LocalDateTime;

public class CacheMapBO {

	private LocalDateTime startTime;
	private Integer coinTypeCode;
	private Integer currencyCode;

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

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

	@Override
	public String toString() {
		return "CacheMapBO [startTime=" + startTime + ", coinTypeCode=" + coinTypeCode + ", currencyCode="
				+ currencyCode + "]";
	}

}
