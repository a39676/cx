package demo.finance.cryptoCoin.data.pojo.po;

import java.time.LocalDateTime;

public class CryptoCoinSymbolLeverage {
    private Long id;

    private String symbol;

    private Integer exchangeCode;

    private Integer leverage;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public Integer getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(Integer exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public Integer getLeverage() {
        return leverage;
    }

    public void setLeverage(Integer leverage) {
        this.leverage = leverage;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}