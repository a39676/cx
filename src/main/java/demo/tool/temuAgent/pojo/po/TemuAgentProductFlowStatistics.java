package demo.tool.temuAgent.pojo.po;

import java.time.LocalDateTime;

public class TemuAgentProductFlowStatistics {
    private Long modelId;

    private Integer stockingCounting;

    private LocalDateTime stockingUpdateTime;

    private Integer internationalStockingCounting;

    private LocalDateTime internationalStockingUpdateTime;

    private Integer selledCounting;

    private LocalDateTime selledUpdateTime;

    private Integer repackageCounting;

    private LocalDateTime repackageUdpateTime;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getStockingCounting() {
        return stockingCounting;
    }

    public void setStockingCounting(Integer stockingCounting) {
        this.stockingCounting = stockingCounting;
    }

    public LocalDateTime getStockingUpdateTime() {
        return stockingUpdateTime;
    }

    public void setStockingUpdateTime(LocalDateTime stockingUpdateTime) {
        this.stockingUpdateTime = stockingUpdateTime;
    }

    public Integer getInternationalStockingCounting() {
        return internationalStockingCounting;
    }

    public void setInternationalStockingCounting(Integer internationalStockingCounting) {
        this.internationalStockingCounting = internationalStockingCounting;
    }

    public LocalDateTime getInternationalStockingUpdateTime() {
        return internationalStockingUpdateTime;
    }

    public void setInternationalStockingUpdateTime(LocalDateTime internationalStockingUpdateTime) {
        this.internationalStockingUpdateTime = internationalStockingUpdateTime;
    }

    public Integer getSelledCounting() {
        return selledCounting;
    }

    public void setSelledCounting(Integer selledCounting) {
        this.selledCounting = selledCounting;
    }

    public LocalDateTime getSelledUpdateTime() {
        return selledUpdateTime;
    }

    public void setSelledUpdateTime(LocalDateTime selledUpdateTime) {
        this.selledUpdateTime = selledUpdateTime;
    }

    public Integer getRepackageCounting() {
        return repackageCounting;
    }

    public void setRepackageCounting(Integer repackageCounting) {
        this.repackageCounting = repackageCounting;
    }

    public LocalDateTime getRepackageUdpateTime() {
        return repackageUdpateTime;
    }

    public void setRepackageUdpateTime(LocalDateTime repackageUdpateTime) {
        this.repackageUdpateTime = repackageUdpateTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}