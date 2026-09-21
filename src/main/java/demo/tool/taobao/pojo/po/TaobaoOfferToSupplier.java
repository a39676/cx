package demo.tool.taobao.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaobaoOfferToSupplier {
    private Long id;

    private Long downstreamBuyerOfferId;

    private Long supplierOfferId;

    private BigDecimal amount;

    private Long merchantId;

    private String remark;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDownstreamBuyerOfferId() {
        return downstreamBuyerOfferId;
    }

    public void setDownstreamBuyerOfferId(Long downstreamBuyerOfferId) {
        this.downstreamBuyerOfferId = downstreamBuyerOfferId;
    }

    public Long getSupplierOfferId() {
        return supplierOfferId;
    }

    public void setSupplierOfferId(Long supplierOfferId) {
        this.supplierOfferId = supplierOfferId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
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