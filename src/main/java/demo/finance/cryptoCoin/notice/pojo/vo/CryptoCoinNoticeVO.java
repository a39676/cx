package demo.finance.cryptoCoin.notice.pojo.vo;

public class CryptoCoinNoticeVO {

	private String pk;

	private String noticeReciver;
	private String noticeReciverPK;

	private Integer timeUnitOfDataWatch;
	private String timeUnitOfDataWatchName;
	private Integer timeRangeOfDataWatch;

	private Integer timeUnitOfNoticeInterval;
	private String timeUnitOfNoticeIntervalName;
	private Integer timeRangeOfNoticeInterval;

	private Long cryptoCoinCode;
	private String cryptoCoinName;

	private Integer currencyCode;
	private String currencyName;

	private Integer noticeCount;
	private String noticeTime;
	private String nextNoticeTime;
	private String validTime;

	private Double maxPrice;
	private Double minPrice;
	private Double fluctuactionSpeedPercentage;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getNoticeReciver() {
		return noticeReciver;
	}

	public void setNoticeReciver(String noticeReciver) {
		this.noticeReciver = noticeReciver;
	}

	public Integer getTimeUnitOfDataWatch() {
		return timeUnitOfDataWatch;
	}

	public String getNoticeReciverPK() {
		return noticeReciverPK;
	}

	public void setNoticeReciverPK(String noticeReciverPK) {
		this.noticeReciverPK = noticeReciverPK;
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

	public Long getCryptoCoinCode() {
		return cryptoCoinCode;
	}

	public void setCryptoCoinCode(Long cryptoCoinCode) {
		this.cryptoCoinCode = cryptoCoinCode;
	}

	public String getCryptoCoinName() {
		return cryptoCoinName;
	}

	public void setCryptoCoinName(String cryptoCoinName) {
		this.cryptoCoinName = cryptoCoinName;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Integer getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
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

	public Double getFluctuactionSpeedPercentage() {
		return fluctuactionSpeedPercentage;
	}

	public void setFluctuactionSpeedPercentage(Double fluctuactionSpeedPercentage) {
		this.fluctuactionSpeedPercentage = fluctuactionSpeedPercentage;
	}

	public String getTimeUnitOfDataWatchName() {
		return timeUnitOfDataWatchName;
	}

	public void setTimeUnitOfDataWatchName(String timeUnitOfDataWatchName) {
		this.timeUnitOfDataWatchName = timeUnitOfDataWatchName;
	}

	public String getTimeUnitOfNoticeIntervalName() {
		return timeUnitOfNoticeIntervalName;
	}

	public void setTimeUnitOfNoticeIntervalName(String timeUnitOfNoticeIntervalName) {
		this.timeUnitOfNoticeIntervalName = timeUnitOfNoticeIntervalName;
	}

	@Override
	public String toString() {
		return "CryptoCoinNoticeVO [pk=" + pk + ", noticeReciver=" + noticeReciver + ", noticeReciverPK="
				+ noticeReciverPK + ", timeUnitOfDataWatch=" + timeUnitOfDataWatch + ", timeUnitOfDataWatchName="
				+ timeUnitOfDataWatchName + ", timeRangeOfDataWatch=" + timeRangeOfDataWatch
				+ ", timeUnitOfNoticeInterval=" + timeUnitOfNoticeInterval + ", timeUnitOfNoticeIntervalName="
				+ timeUnitOfNoticeIntervalName + ", timeRangeOfNoticeInterval=" + timeRangeOfNoticeInterval
				+ ", cryptoCoinCode=" + cryptoCoinCode + ", cryptoCoinName=" + cryptoCoinName + ", currencyCode="
				+ currencyCode + ", currencyName=" + currencyName + ", noticeCount=" + noticeCount + ", noticeTime="
				+ noticeTime + ", nextNoticeTime=" + nextNoticeTime + ", validTime=" + validTime + ", maxPrice="
				+ maxPrice + ", minPrice=" + minPrice + ", fluctuactionSpeedPercentage=" + fluctuactionSpeedPercentage
				+ "]";
	}

}
