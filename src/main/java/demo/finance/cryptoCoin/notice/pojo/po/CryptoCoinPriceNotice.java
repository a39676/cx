package demo.finance.cryptoCoin.notice.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinPriceNotice {
    private Long id;

    private Integer coinType;

    private Integer currencyType;

    private BigDecimal maxPrice;

    private BigDecimal minPrice;

    private Integer timeUnitOfDataWatch;

    private Integer timeRangeOfDataWatch;

    private BigDecimal fluctuationSpeedPercentage;

    private String telegramChatPk;

    private Integer noticeCount;

    private Integer timeUnitOfNoticeInterval;

    private Integer timeRangeOfNoticeInterval;

    private LocalDateTime validTime;

    private LocalDateTime noticeTime;

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

    public String getTelegramChatPk() {
        return telegramChatPk;
    }

    public void setTelegramChatPk(String telegramChatPk) {
        this.telegramChatPk = telegramChatPk == null ? null : telegramChatPk.trim();
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