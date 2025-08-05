package demo.tool.temuAgent.pojo.po;

import java.time.LocalDateTime;

public class TemuAgentProductFlowHistory {
    private Long id;

    private Long modelId;

    private Integer flowTypeCode;

    private Integer flowCounting;

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

    public Integer getFlowTypeCode() {
        return flowTypeCode;
    }

    public void setFlowTypeCode(Integer flowTypeCode) {
        this.flowTypeCode = flowTypeCode;
    }

    public Integer getFlowCounting() {
        return flowCounting;
    }

    public void setFlowCounting(Integer flowCounting) {
        this.flowCounting = flowCounting;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}