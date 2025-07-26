package demo.tool.temuAgent.pojo.dto;

import java.math.BigDecimal;

public class TemuAgentCreateOrUpdateProductModelDTO {

	private Long productId;
	private Long productModelId;
	private Integer unitTypeCode;
	private Integer unitCounting;
	private String spu;
	private String sku;
	private String skc;
	private BigDecimal declearedPrice;
	private BigDecimal packingFee;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
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
		this.spu = spu;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkc() {
		return skc;
	}

	public void setSkc(String skc) {
		this.skc = skc;
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

	@Override
	public String toString() {
		return "TemuAgentCreateOrUpdateProductModelDTO [productId=" + productId + ", productModelId=" + productModelId
				+ ", unitTypeCode=" + unitTypeCode + ", unitCounting=" + unitCounting + ", spu=" + spu + ", sku=" + sku
				+ ", skc=" + skc + ", declearedPrice=" + declearedPrice + ", packingFee=" + packingFee + "]";
	}

}
