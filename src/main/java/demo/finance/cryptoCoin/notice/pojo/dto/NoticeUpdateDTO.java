package demo.finance.cryptoCoin.notice.pojo.dto;

public class NoticeUpdateDTO {

	private String pk;

	private Integer cryptoCoinCode;
	private Integer currencyCode;

	private Double maxPrice;
	private Double minPrice;

	private Integer timeRangeOfDataWatch;
	private Integer timeUnitOfDataWatch;

	private Integer timeRangeOfNoticeInterval;
	private Integer timeUnitOfNoticeInterval;

	private Double fluctuactionSpeedPercentage;

	private Integer noticeCount;

	private String nextNoticeTime;
	private String validTime;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public Integer getTimeRangeOfDataWatch() {
		return timeRangeOfDataWatch;
	}

	public void setTimeRangeOfDataWatch(Integer timeRangeOfDataWatch) {
		this.timeRangeOfDataWatch = timeRangeOfDataWatch;
	}

	public Integer getTimeUnitOfDataWatch() {
		return timeUnitOfDataWatch;
	}

	public void setTimeUnitOfDataWatch(Integer timeUnitOfDataWatch) {
		this.timeUnitOfDataWatch = timeUnitOfDataWatch;
	}

	public Integer getTimeRangeOfNoticeInterval() {
		return timeRangeOfNoticeInterval;
	}

	public void setTimeRangeOfNoticeInterval(Integer timeRangeOfNoticeInterval) {
		this.timeRangeOfNoticeInterval = timeRangeOfNoticeInterval;
	}

	public Integer getTimeUnitOfNoticeInterval() {
		return timeUnitOfNoticeInterval;
	}

	public void setTimeUnitOfNoticeInterval(Integer timeUnitOfNoticeInterval) {
		this.timeUnitOfNoticeInterval = timeUnitOfNoticeInterval;
	}

	public Integer getCryptoCoinCode() {
		return cryptoCoinCode;
	}

	public void setCryptoCoinCode(Integer cryptoCoinCode) {
		this.cryptoCoinCode = cryptoCoinCode;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}

	public Double getFluctuactionSpeedPercentage() {
		return fluctuactionSpeedPercentage;
	}

	public void setFluctuactionSpeedPercentage(Double fluctuactionSpeedPercentage) {
		this.fluctuactionSpeedPercentage = fluctuactionSpeedPercentage;
	}

	public String getNextNoticeTime() {
		return nextNoticeTime;
	}

	public void setNextNoticeTime(String nextNoticeTime) {
		this.nextNoticeTime = nextNoticeTime;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	@Override
	public String toString() {
		return "NoticeUpdateDTO [pk=" + pk + ", cryptoCoinCode=" + cryptoCoinCode + ", currencyCode=" + currencyCode
				+ ", maxPrice=" + maxPrice + ", minPrice=" + minPrice + ", timeRangeOfDataWatch=" + timeRangeOfDataWatch
				+ ", timeUnitOfDataWatch=" + timeUnitOfDataWatch + ", timeRangeOfNoticeInterval="
				+ timeRangeOfNoticeInterval + ", timeUnitOfNoticeInterval=" + timeUnitOfNoticeInterval
				+ ", fluctuactionSpeedPercentage=" + fluctuactionSpeedPercentage + ", noticeCount=" + noticeCount
				+ ", nextNoticeTime=" + nextNoticeTime + ", validTime=" + validTime + "]";
	}

}
