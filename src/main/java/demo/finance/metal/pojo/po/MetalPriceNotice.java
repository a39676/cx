package demo.finance.metal.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MetalPriceNotice {
    private Long id;

    private Integer metalType;

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

    public Integer getMetalType() {
        return metalType;
    }

    public void setMetalType(Integer metalType) {
        this.metalType = metalType;
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