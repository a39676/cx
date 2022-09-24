package demo.finance.currencyExchangeRate.notice.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyExchangeRateNotice {
    private Long id;

    private Integer currencyFrom;

    private Integer currencyTo;

    private BigDecimal maxRate;

    private BigDecimal minRate;

    private Integer timeUnitOfDataWatch;

    private Integer timeRangeOfDataWatch;

    private BigDecimal fluctuationSpeedPercentage;

    private Long telegramChatId;

    private String telegramBotName;

    private Integer noticeCount;

    private Integer timeUnitOfNoticeInterval;

    private Integer timeRangeOfNoticeInterval;

    private LocalDateTime validTime;

    private LocalDateTime noticeTime;

    private LocalDateTime nextNoticeTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(Integer currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public Integer getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(Integer currencyTo) {
        this.currencyTo = currencyTo;
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

    public BigDecimal getFluctuationSpeedPercentage() {
        return fluctuationSpeedPercentage;
    }

    public void setFluctuationSpeedPercentage(BigDecimal fluctuationSpeedPercentage) {
        this.fluctuationSpeedPercentage = fluctuationSpeedPercentage;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(Long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public String getTelegramBotName() {
        return telegramBotName;
    }

    public void setTelegramBotName(String telegramBotName) {
        this.telegramBotName = telegramBotName == null ? null : telegramBotName.trim();
    }

    public Integer getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(Integer noticeCount) {
        this.noticeCount = noticeCount;
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

    public LocalDateTime getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDateTime validTime) {
        this.validTime = validTime;
    }

    public LocalDateTime getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(LocalDateTime noticeTime) {
        this.noticeTime = noticeTime;
    }

    public LocalDateTime getNextNoticeTime() {
        return nextNoticeTime;
    }

    public void setNextNoticeTime(LocalDateTime nextNoticeTime) {
        this.nextNoticeTime = nextNoticeTime;
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
}