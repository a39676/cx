package demo.finance.currencyExchangeRate.data.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyExchangeRate1day {
    private Long id;

    private Integer currencyFrom;

    private Integer currencyTo;

    private BigDecimal sellAvgPrice;

    private BigDecimal buyAvgPrice;

    private BigDecimal sellHighPrice;

    private BigDecimal sellLowPrice;

    private BigDecimal buyHighPrice;

    private BigDecimal buyLowPrice;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

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

    public BigDecimal getSellAvgPrice() {
        return sellAvgPrice;
    }

    public void setSellAvgPrice(BigDecimal sellAvgPrice) {
        this.sellAvgPrice = sellAvgPrice;
    }

    public BigDecimal getBuyAvgPrice() {
        return buyAvgPrice;
    }

    public void setBuyAvgPrice(BigDecimal buyAvgPrice) {
        this.buyAvgPrice = buyAvgPrice;
    }

    public BigDecimal getSellHighPrice() {
        return sellHighPrice;
    }

    public void setSellHighPrice(BigDecimal sellHighPrice) {
        this.sellHighPrice = sellHighPrice;
    }

    public BigDecimal getSellLowPrice() {
        return sellLowPrice;
    }

    public void setSellLowPrice(BigDecimal sellLowPrice) {
        this.sellLowPrice = sellLowPrice;
    }

    public BigDecimal getBuyHighPrice() {
        return buyHighPrice;
    }

    public void setBuyHighPrice(BigDecimal buyHighPrice) {
        this.buyHighPrice = buyHighPrice;
    }

    public BigDecimal getBuyLowPrice() {
        return buyLowPrice;
    }

    public void setBuyLowPrice(BigDecimal buyLowPrice) {
        this.buyLowPrice = buyLowPrice;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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