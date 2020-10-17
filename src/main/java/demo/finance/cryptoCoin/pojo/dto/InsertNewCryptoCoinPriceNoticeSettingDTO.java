package demo.finance.cryptoCoin.pojo.dto;

import java.math.BigDecimal;

public class InsertNewCryptoCoinPriceNoticeSettingDTO {

	private Integer coinType;

	private Integer currencyType;

	private BigDecimal maxPrice;

	private BigDecimal minPrice;

	/**
	 * 参考原价 用于条件: 价格波动幅度条件设定
	 */
	private Double originalPrice;
	/**
	 * 用于条件: n分钟内波动幅度超过 m%的条件设定
	 */
	private Double percentage;
	/**
	 * 用于条件: n分钟内波动幅度超过 m%的条件设定
	 */
	private Integer minuteRange;

	private String email;

	private String validTime;

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

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMinuteRange() {
		return minuteRange;
	}

	public void setMinuteRange(Integer minuteRange) {
		this.minuteRange = minuteRange;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Override
	public String toString() {
		return "InsertNewCryptoCoinPriceNoticeSettingDTO [coinType=" + coinType + ", currencyType=" + currencyType
				+ ", maxPrice=" + maxPrice + ", minPrice=" + minPrice + ", originalPrice=" + originalPrice
				+ ", percentage=" + percentage + ", minuteRange=" + minuteRange + ", email=" + email + ", validTime="
				+ validTime + "]";
	}

}
