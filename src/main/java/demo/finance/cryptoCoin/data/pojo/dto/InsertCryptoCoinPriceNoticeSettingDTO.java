package demo.finance.cryptoCoin.data.pojo.dto;

import java.math.BigDecimal;

import auxiliaryCommon.pojo.type.TimeUnitType;

public class InsertCryptoCoinPriceNoticeSettingDTO {

	private Integer coinType;

	private Integer currencyType;

	private BigDecimal maxPrice;

	private BigDecimal minPrice;

	/**
	 * 参考原价 用于条件: 价格波动幅度条件设定
	 */
	private Double originalPrice;
	/**
	 * 用于条件: 价格波动幅度%的条件设定
	 */
	private Double pricePercentage;

	/**
	 * 时间单位设定 {@link TimeUnitType}
	 */
	private Integer timeUnitOfDataWatch;

	private Integer timeRangeOfDataWatch;

	/**
	 * 时间单位设定 {@link TimeUnitType}
	 */
	private Integer timeUnitOfNoticeInterval;

	private Integer timeRangeOfNoticeInterval;

	/**
	 * 用于条件: n个时间单位内波动幅度超过 m%的条件设定 触发例: 最新价 = x, n(分钟)前收盘价 = o, 如果 x > o * (1 + m%)
	 * || x < o * (1 - m%),
	 * 
	 */
	private Integer priceRange;

	/**
	 * 用于条件: n个时间单位内升降速度超过 m%的条件设定 触发例: n(分钟)内, 最高最低价差别超过 m%
	 */
	private Double fluctuationSpeedPercentage;

	private String telegramChatPK;

	private Integer noticeCount;

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

	public Integer getTimeUnitOfDataWatch() {
		return timeUnitOfDataWatch;
	}

	public void setTimeUnitOfDataWatch(Integer timeUnitOfDataWatch) {
		this.timeUnitOfDataWatch = timeUnitOfDataWatch;
	}

	public Integer getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(Integer priceRange) {
		this.priceRange = priceRange;
	}

	public Double getFluctuationSpeedPercentage() {
		return fluctuationSpeedPercentage;
	}

	public void setFluctuationSpeedPercentage(Double fluctuationSpeedPercentage) {
		this.fluctuationSpeedPercentage = fluctuationSpeedPercentage;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public Integer getTimeRangeOfDataWatch() {
		return timeRangeOfDataWatch;
	}

	public void setTimeRangeOfDataWatch(Integer timeRangeOfDataWatch) {
		this.timeRangeOfDataWatch = timeRangeOfDataWatch;
	}

	public Integer getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}

	public String getTelegramChatPK() {
		return telegramChatPK;
	}

	public void setTelegramChatPK(String telegramChatPK) {
		this.telegramChatPK = telegramChatPK;
	}

	public Integer getTimeUnitOfNoticeInterval() {
		return timeUnitOfNoticeInterval;
	}

	public void setTimeUnitOfNoticeInterval(Integer timeUnitOfNoticeInterval) {
		this.timeUnitOfNoticeInterval = timeUnitOfNoticeInterval;
	}

	public Integer getTimeRangeOfNoticeInterval() {
		return timeRangeOfNoticeInterval;
	}

	public void setTimeRangeOfNoticeInterval(Integer timeRangeOfNoticeInterval) {
		this.timeRangeOfNoticeInterval = timeRangeOfNoticeInterval;
	}

	@Override
	public String toString() {
		return "InsertCryptoCoinPriceNoticeSettingDTO [coinType=" + coinType + ", currencyType=" + currencyType
				+ ", maxPrice=" + maxPrice + ", minPrice=" + minPrice + ", originalPrice=" + originalPrice
				+ ", pricePercentage=" + pricePercentage + ", timeUnitOfDataWatch=" + timeUnitOfDataWatch
				+ ", timeRangeOfDataWatch=" + timeRangeOfDataWatch + ", timeUnitOfNoticeInterval="
				+ timeUnitOfNoticeInterval + ", timeRangeOfNoticeInterval=" + timeRangeOfNoticeInterval
				+ ", priceRange=" + priceRange + ", fluctuationSpeedPercentage=" + fluctuationSpeedPercentage
				+ ", telegramChatPK=" + telegramChatPK + ", noticeCount=" + noticeCount + ", validTime=" + validTime
				+ "]";
	}

}
