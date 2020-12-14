package demo.finance.cryptoCoin.data.pojo.dto;

public class InsertDayCryptoCoinPriceNoticeSettingDTO {

	private Integer coinType;

	private Integer currencyType;

	/**
	 * 参考原价 用于条件: 价格波动幅度条件设定
	 */
	private Double originalPrice;
	/**
	 * 用于条件: 价格波动幅度的条件设定
	 */
	private Double pricePercentage;
	/**
	 * 用于条件: n日内波动幅度超过 m%的条件设定
	 */
	private Integer dayRange;

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

	public Integer getDayRange() {
		return dayRange;
	}

	public void setDayRange(Integer dayRange) {
		this.dayRange = dayRange;
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

	@Override
	public String toString() {
		return "InsertDayCryptoCoinPriceNoticeSettingDTO [coinType=" + coinType + ", currencyType=" + currencyType
				+ ", originalPrice=" + originalPrice + ", pricePercentage=" + pricePercentage + ", dayRange=" + dayRange
				+ ", email=" + email + ", validTime=" + validTime + "]";
	}

}
