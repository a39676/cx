package demo.finance.cryptoCoin.mining.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoCoinMiningOutput {
    private Long id;

    private Long miningMachineId;

    private LocalDateTime outputTime;

    private BigDecimal outputCounting;

    private LocalDateTime createTime;

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

    public LocalDateTime getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(LocalDateTime outputTime) {
        this.outputTime = outputTime;
    }

    public BigDecimal getOutputCounting() {
        return outputCounting;
    }

    public void setOutputCounting(BigDecimal outputCounting) {
        this.outputCounting = outputCounting;
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