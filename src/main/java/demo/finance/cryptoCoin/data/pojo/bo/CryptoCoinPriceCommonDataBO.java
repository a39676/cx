package demo.finance.cryptoCoin.data.pojo.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinPriceCommonDataBO implements Comparable<CryptoCoinPriceCommonDataBO> {
	private Long id;

	private Integer coinType;

	private Integer currencyType;

	private BigDecimal startPrice;

	private BigDecimal endPrice;

	private BigDecimal highPrice;

	private BigDecimal lowPrice;

	private BigDecimal volume;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private LocalDateTime createTime;

	private Boolean isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCoinType() {
		return coinType;
	}

	public void setCoinType(Integer coinType) {
		this.coinType = coinType;
	}

	public Integer getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public BigDecimal getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(BigDecimal endPrice) {
		this.endPrice = endPrice;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
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

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "CryptoCoinPriceCommonDataBO [id=" + id + ", coinType=" + coinType + ", currencyType=" + currencyType
				+ ", startPrice=" + startPrice + ", endPrice=" + endPrice + ", highPrice=" + highPrice + ", lowPrice="
				+ lowPrice + ", volume=" + volume + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createTime=" + createTime + ", isDelete=" + isDelete + "]";
	}

	@Override
	public int compareTo(CryptoCoinPriceCommonDataBO o) {
		return compareStartTime(o, this);
	}

	private int compareStartTime(CryptoCoinPriceCommonDataBO o, CryptoCoinPriceCommonDataBO t) {
		if (o.getStartTime() == null || t.getStartTime() == null) {
			if (o.getStartTime() == null && t.getStartTime() == null) {
				return 0;
			} else if (o.getStartTime() == null) {
				return -1;
			} else if (this.getStartTime() == null) {
				return 1;
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