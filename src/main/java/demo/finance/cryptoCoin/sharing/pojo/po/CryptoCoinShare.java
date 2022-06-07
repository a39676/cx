package demo.finance.cryptoCoin.sharing.pojo.po;

import java.time.LocalDateTime;

public class CryptoCoinShare {
    private Long id;

    private Long miningMachineId;

    private LocalDateTime outputTime;

    private String filePath;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
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