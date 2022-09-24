package demo.finance.currencyExchangeRate.notice.pojo.dto;

import java.math.BigDecimal;

import auxiliaryCommon.pojo.type.TimeUnitType;

public class InsertCurrencyExchangeRateNoticeSettingDTO {

	private String fromCurrencyType;

	private String toCurrencyType;

	private BigDecimal maxRate;

	private BigDecimal minRate;

	/**
	 * 参考原价 用于条件: 价格波动幅度条件设定
	 */
	private Double originalRate;
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

	private String startNoticeTime;

	public String getFromCurrencyType() {
		return fromCurrencyType;
	}

	public void setFromCurrencyType(String fromCurrencyType) {
		this.fromCurrencyType = fromCurrencyType;
	}

	public String getToCurrencyType() {
		return toCurrencyType;
	}

	public void setToCurrencyType(String toCurrencyType) {
		this.toCurrencyType = toCurrencyType;
	}

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

	public Double getOriginalRate() {
		return originalRate;
	}

	public void setOriginalRate(Double originalRate) {
		this.originalRate = originalRate;
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

	public Integer getTimeRangeOfDataWatch() {
		return timeRangeOfDataWatch;
	}

	public void setTimeRangeOfDataWatch(Integer timeRangeOfDataWatch) {
		this.timeRangeOfDataWatch = timeRangeOfDataWatch;
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

	public String getTelegramChatPK() {
		return telegramChatPK;
	}

	public void setTelegramChatPK(String telegramChatPK) {
		this.telegramChatPK = telegramChatPK;
	}

	public Integer getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getStartNoticeTime() {
		return startNoticeTime;
	}

	public void setStartNoticeTime(String startNoticeTime) {
		this.startNoticeTime = startNoticeTime;
	}

	@Override
	public String toString() {
		return "InsertCurrencyExchangeRateNoticeSettingDTO [fromCurrencyType=" + fromCurrencyType + ", toCurrencyType="
				+ toCurrencyType + ", maxRate=" + maxRate + ", minRate=" + minRate + ", originalRate=" + originalRate
				+ ", pricePercentage=" + pricePercentage + ", timeUnitOfDataWatch=" + timeUnitOfDataWatch
				+ ", timeRangeOfDataWatch=" + timeRangeOfDataWatch + ", timeUnitOfNoticeInterval="
				+ timeUnitOfNoticeInterval + ", timeRangeOfNoticeInterval=" + timeRangeOfNoticeInterval
				+ ", priceRange=" + priceRange + ", fluctuationSpeedPercentage=" + fluctuationSpeedPercentage
				+ ", telegramChatPK=" + telegramChatPK + ", noticeCount=" + noticeCount + ", validTime=" + validTime
				+ ", startNoticeTime=" + startNoticeTime + "]";
	}

}
