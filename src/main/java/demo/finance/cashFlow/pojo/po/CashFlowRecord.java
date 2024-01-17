package demo.finance.cashFlow.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CashFlowRecord {
    private Long id;

    private Long userId;

    private LocalDateTime activeTime;

    private LocalDateTime expiredTime;

    private BigDecimal flowAmount;

    private Boolean timeUnitCode;

    private Integer timeCounting;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(LocalDateTime activeTime) {
        this.activeTime = activeTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public BigDecimal getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(BigDecimal flowAmount) {
        this.flowAmount = flowAmount;
    }

    public Boolean getTimeUnitCode() {
        return timeUnitCode;
    }

    public void setTimeUnitCode(Boolean timeUnitCode) {
        this.timeUnitCode = timeUnitCode;
    }

    public Integer getTimeCounting() {
        return timeCounting;
    }

    public void setTimeCounting(Integer timeCounting) {
        this.timeCounting = timeCounting;
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