package demo.finance.cryptoCoin.data.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinBigTrade {
    private String symbol;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal quantity;

    private Boolean isMaker;

    private LocalDateTime eventTime;

    private LocalDateTime createTime;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsMaker() {
        return isMaker;
    }

    public void setIsMaker(Boolean isMaker) {
        this.isMaker = isMaker;
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