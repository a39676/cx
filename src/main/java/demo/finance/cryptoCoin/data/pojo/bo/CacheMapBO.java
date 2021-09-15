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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coinTypeCode == null) ? 0 : coinTypeCode.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheMapBO other = (CacheMapBO) obj;
		if (coinTypeCode == null) {
			if (other.coinTypeCode != null)
				return false;
		} else if (!coinTypeCode.equals(other.coinTypeCode))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	

}
