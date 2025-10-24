package demo.tool.taobao.pojo.po;

import java.time.LocalDateTime;

public class TaobaoProductSource {
    private Long id;

    private Long commodityId;

    private Long sourceId;

    private String commodityName;

    private String commodityImgName;

    private Boolean includePostage;

    private String remark;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    public String getCommodityImgName() {
        return commodityImgName;
    }

    public void setCommodityImgName(String commodityImgName) {
        this.commodityImgName = commodityImgName == null ? null : commodityImgName.trim();
    }

    public Boolean getIncludePostage() {
        return includePostage;
    }

    public void setIncludePostage(Boolean includePostage) {
        this.includePostage = includePostage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}