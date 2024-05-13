package demo.finance.cryptoCoin.data.pojo.po;

import java.time.LocalDateTime;

public class CryptoCoinBigMoveKey {
    private String symbol;

    private LocalDateTime eventTime;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}