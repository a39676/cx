package demo.finance.cryptoCoin.mining.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinAllocationAssistantMatchMachine {
    private Long id;

    private Long assistantId;

    private Long miningMachineId;

    private BigDecimal partingCount;

    private BigDecimal commissionFeeRate;

    private LocalDateTime udpateTime;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Long assistantId) {
        this.assistantId = assistantId;
    }

    public Long getMiningMachineId() {
        return miningMachineId;
    }

    public void setMiningMachineId(Long miningMachineId) {
        this.miningMachineId = miningMachineId;
    }

    public BigDecimal getPartingCount() {
        return partingCount;
    }

    public void setPartingCount(BigDecimal partingCount) {
        this.partingCount = partingCount;
    }

    public BigDecimal getCommissionFeeRate() {
        return commissionFeeRate;
    }

    public void setCommissionFeeRate(BigDecimal commissionFeeRate) {
        this.commissionFeeRate = commissionFeeRate;
    }

    public LocalDateTime getUdpateTime() {
        return udpateTime;
    }

    public void setUdpateTime(LocalDateTime udpateTime) {
        this.udpateTime = udpateTime;
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