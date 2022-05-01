package demo.finance.cryptoCoin.sharing.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinMiningMachineInfo {
    private Long id;

    private Long miningMachineId;

    private BigDecimal handlingFeeRate;

    private Integer partingCount;

    private LocalDateTime createTime;

    private LocalDateTime udpateTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMiningMachineId() {
        return miningMachineId;
    }

    public void setMiningMachineId(Long miningMachineId) {
        this.miningMachineId = miningMachineId;
    }

    public BigDecimal getHandlingFeeRate() {
        return handlingFeeRate;
    }

    public void setHandlingFeeRate(BigDecimal handlingFeeRate) {
        this.handlingFeeRate = handlingFeeRate;
    }

    public Integer getPartingCount() {
        return partingCount;
    }

    public void setPartingCount(Integer partingCount) {
        this.partingCount = partingCount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUdpateTime() {
        return udpateTime;
    }

    public void setUdpateTime(LocalDateTime udpateTime) {
        this.udpateTime = udpateTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}