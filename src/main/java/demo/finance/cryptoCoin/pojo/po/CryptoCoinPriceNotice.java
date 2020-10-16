package demo.finance.cryptoCoin.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinPriceNotice {
    private Long id;

    private Integer coinType;

    private Integer currencyType;

    private BigDecimal maxGramPrice;

    private BigDecimal minGramPrice;

    private String email;

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

    public BigDecimal getMaxGramPrice() {
        return maxGramPrice;
    }

    public void setMaxGramPrice(BigDecimal maxGramPrice) {
        this.maxGramPrice = maxGramPrice;
    }

    public BigDecimal getMinGramPrice() {
        return minGramPrice;
    }

    public void setMinGramPrice(BigDecimal minGramPrice) {
        this.minGramPrice = minGramPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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