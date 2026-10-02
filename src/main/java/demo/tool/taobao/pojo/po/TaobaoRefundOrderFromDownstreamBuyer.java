package demo.tool.taobao.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaobaoRefundOrderFromDownstreamBuyer {
    private Long id;

    private Long refundOrderId;

    private Long sourceOrderId;

    private BigDecimal amount;

    private LocalDateTime refundCreateTime;

    private Integer regionId1;

    private Integer regionId2;

    private Integer afterTransitRegionId;

    private String remark;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(Long refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    public Long getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(Long sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getRefundCreateTime() {
        return refundCreateTime;
    }

    public void setRefundCreateTime(LocalDateTime refundCreateTime) {
        this.refundCreateTime = refundCreateTime;
    }

    public Integer getRegionId1() {
        return regionId1;
    }

    public void setRegionId1(Integer regionId1) {
        this.regionId1 = regionId1;
    }

    public Integer getRegionId2() {
        return regionId2;
    }

    public void setRegionId2(Integer regionId2) {
        this.regionId2 = regionId2;
    }

    public Integer getAfterTransitRegionId() {
        return afterTransitRegionId;
    }

    public void setAfterTransitRegionId(Integer afterTransitRegionId) {
        this.afterTransitRegionId = afterTransitRegionId;
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

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}