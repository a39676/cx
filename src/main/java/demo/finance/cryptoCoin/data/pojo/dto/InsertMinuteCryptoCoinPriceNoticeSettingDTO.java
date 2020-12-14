package demo.finance.cryptoCoin.data.pojo.dto;

import java.math.BigDecimal;

public class InsertMinuteCryptoCoinPriceNoticeSettingDTO {

	private Integer coinType;

	private Integer currencyType;

	private BigDecimal maxPrice;

	private BigDecimal minPrice;

	/**
	 * 参考原价 用于条件: 价格波动幅度条件设定
	 */
	private Double originalPrice;
	/**
	 * 用于条件: 价格波动幅度的条件设定
	 */
	private Double pricePercentage;
	/**
	 * 用于条件: n分钟内波动幅度超过 m%的条件设定
	 */
	private Integer minuteRange;
	
	/**
	 * 用于条件: n分钟内波动幅度超过 m%的条件设定
	 */
	private Double fluctuationSpeedPercentage;

	private String email;

	private String validTime;

	@Override
	public String toString() {
		return "InsertNewCryptoCoinPriceNoticeSettingDTO [coinType=" + coinType + ", currencyType=" + currencyType
				+ ", maxPrice=" + maxPrice + ", minPrice=" + minPrice + ", originalPrice=" + originalPrice
				+ ", pricePercentage=" + pricePercentage + ", minuteRange=" + minuteRange
				+ ", fluctuationSpeedPercentage=" + fluctuationSpeedPercentage + ", email=" + email + ", validTime="
				+ validTime + "]";
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

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getPricePercentage() {
		return pricePercentage;
	}

	public void setPricePercentage(Double pricePercentage) {
		this.pricePercentage = pricePercentage;
	}

	public Integer getMinuteRange() {
		return minuteRange;
	}

	public void setMinuteRange(Integer minuteRange) {
		this.minuteRange = minuteRange;
	}

	public Double getFluctuationSpeedPercentage() {
		return fluctuationSpeedPercentage;
	}

	public void setFluctuationSpeedPercentage(Double fluctuationSpeedPercentage) {
		this.fluctuationSpeedPercentage = fluctuationSpeedPercentage;
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

}
