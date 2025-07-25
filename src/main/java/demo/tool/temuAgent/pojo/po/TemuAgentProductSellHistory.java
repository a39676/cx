package demo.tool.temuAgent.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemuAgentProductSellHistory {
    private Long id;

    private Long modelId;

    private Integer counting;

    private BigDecimal price;

    private Boolean isReturn;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getCounting() {
        return counting;
    }

    public void setCounting(Integer counting) {
        this.counting = counting;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Boolean isReturn) {
        this.isReturn = isReturn;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}