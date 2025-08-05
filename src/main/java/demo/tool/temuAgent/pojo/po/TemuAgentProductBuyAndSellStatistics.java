package demo.tool.temuAgent.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemuAgentProductBuyAndSellStatistics {
    private Long modelId;

    private Integer buyCounting;

    private BigDecimal avgBuyPrice;

    private BigDecimal highestBuyPrice;

    private BigDecimal lowestBuyPrice;

    private BigDecimal lastBuyPrice;

    private Integer sellCounting;

    private BigDecimal avgSellPrice;

    private BigDecimal highestSellPrice;

    private BigDecimal lowestSellPrice;

    private BigDecimal lastSellPrice;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getBuyCounting() {
        return buyCounting;
    }

    public void setBuyCounting(Integer buyCounting) {
        this.buyCounting = buyCounting;
    }

    public BigDecimal getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(BigDecimal avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public BigDecimal getHighestBuyPrice() {
        return highestBuyPrice;
    }

    public void setHighestBuyPrice(BigDecimal highestBuyPrice) {
        this.highestBuyPrice = highestBuyPrice;
    }

    public BigDecimal getLowestBuyPrice() {
        return lowestBuyPrice;
    }

    public void setLowestBuyPrice(BigDecimal lowestBuyPrice) {
        this.lowestBuyPrice = lowestBuyPrice;
    }

    public BigDecimal getLastBuyPrice() {
        return lastBuyPrice;
    }

    public void setLastBuyPrice(BigDecimal lastBuyPrice) {
        this.lastBuyPrice = lastBuyPrice;
    }

    public Integer getSellCounting() {
        return sellCounting;
    }

    public void setSellCounting(Integer sellCounting) {
        this.sellCounting = sellCounting;
    }

    public BigDecimal getAvgSellPrice() {
        return avgSellPrice;
    }

    public void setAvgSellPrice(BigDecimal avgSellPrice) {
        this.avgSellPrice = avgSellPrice;
    }

    public BigDecimal getHighestSellPrice() {
        return highestSellPrice;
    }

    public void setHighestSellPrice(BigDecimal highestSellPrice) {
        this.highestSellPrice = highestSellPrice;
    }

    public BigDecimal getLowestSellPrice() {
        return lowestSellPrice;
    }

    public void setLowestSellPrice(BigDecimal lowestSellPrice) {
        this.lowestSellPrice = lowestSellPrice;
    }

    public BigDecimal getLastSellPrice() {
        return lastSellPrice;
    }

    public void setLastSellPrice(BigDecimal lastSellPrice) {
        this.lastSellPrice = lastSellPrice;
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