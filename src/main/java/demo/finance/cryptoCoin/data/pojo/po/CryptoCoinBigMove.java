package demo.finance.cryptoCoin.data.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinBigMove {
    private String symbol;

    private BigDecimal rate;

    private Integer timeRange;

    private Integer timeUnitCode;

    private LocalDateTime eventTime;

    private LocalDateTime createTime;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Integer timeRange) {
        this.timeRange = timeRange;
    }

    public Integer getTimeUnitCode() {
        return timeUnitCode;
    }

    public void setTimeUnitCode(Integer timeUnitCode) {
        this.timeUnitCode = timeUnitCode;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}