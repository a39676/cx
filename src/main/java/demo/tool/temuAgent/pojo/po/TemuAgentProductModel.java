package demo.tool.temuAgent.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemuAgentProductModel {
    private Long id;

    private Long productId;

    private Integer unitTypeCode;

    private Integer unitCounting;

    private String spu;

    private String sku;

    private String skc;

    private BigDecimal purchasePrice;

    private BigDecimal declearedPrice;

    private BigDecimal packingFee;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getUnitTypeCode() {
        return unitTypeCode;
    }

    public void setUnitTypeCode(Integer unitTypeCode) {
        this.unitTypeCode = unitTypeCode;
    }

    public Integer getUnitCounting() {
        return unitCounting;
    }

    public void setUnitCounting(Integer unitCounting) {
        this.unitCounting = unitCounting;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu == null ? null : spu.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public String getSkc() {
        return skc;
    }

    public void setSkc(String skc) {
        this.skc = skc == null ? null : skc.trim();
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getDeclearedPrice() {
        return declearedPrice;
    }

    public void setDeclearedPrice(BigDecimal declearedPrice) {
        this.declearedPrice = declearedPrice;
    }

    public BigDecimal getPackingFee() {
        return packingFee;
    }

    public void setPackingFee(BigDecimal packingFee) {
        this.packingFee = packingFee;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}