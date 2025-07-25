package demo.tool.temuAgent.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemuAgentProductModelStatisticsVO {

	private Long modelId;
	private Long productId;

	private Integer unitTypeCode;
	private String unitTypeName;
	private Integer unitCounting;
	private String spu;
	private String sku;
	private String skc;
	private BigDecimal purchasePrice;
	private BigDecimal declearedPrice;
	private BigDecimal packingFee;

	private Integer stockingCounting;
	private LocalDateTime stockingUpdateTime;
	private Integer internationalStockingCounting;
	private LocalDateTime internationalStockingUpdateTime;
	private Integer selledCounting;
	private LocalDateTime selledUpdateTime;
	private Integer repackageCounting;
	private LocalDateTime repackageUdpateTime;

	private String productName;
	private BigDecimal totalCost;

	private BigDecimal avgPrice;
	private BigDecimal highestPrice;
	private BigDecimal lowestPrice;
	private BigDecimal lastPrice;

	private BigDecimal totalSelledAmount;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
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

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public BigDecimal getHighestPrice() {
		return highestPrice;
	}

	public void setHighestPrice(BigDecimal highestPrice) {
		this.highestPrice = highestPrice;
	}

	public BigDecimal getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(BigDecimal lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getTotalSelledAmount() {
		return totalSelledAmount;
	}

	public void setTotalSelledAmount(BigDecimal totalSelledAmount) {
		this.totalSelledAmount = totalSelledAmount;
	}

	@Override
	public String toString() {
		return "TemuAgentProductModelStatisticsVO [modelId=" + modelId + ", productId=" + productId + ", unitTypeCode="
				+ unitTypeCode + ", unitTypeName=" + unitTypeName + ", unitCounting=" + unitCounting + ", spu=" + spu
				+ ", sku=" + sku + ", skc=" + skc + ", purchasePrice=" + purchasePrice + ", declearedPrice="
				+ declearedPrice + ", packingFee=" + packingFee + ", stockingCounting=" + stockingCounting
				+ ", stockingUpdateTime=" + stockingUpdateTime + ", internationalStockingCounting="
				+ internationalStockingCounting + ", internationalStockingUpdateTime=" + internationalStockingUpdateTime
				+ ", selledCounting=" + selledCounting + ", selledUpdateTime=" + selledUpdateTime
				+ ", repackageCounting=" + repackageCounting + ", repackageUdpateTime=" + repackageUdpateTime
				+ ", productName=" + productName + ", totalCost=" + totalCost + ", avgPrice=" + avgPrice
				+ ", highestPrice=" + highestPrice + ", lowestPrice=" + lowestPrice + ", lastPrice=" + lastPrice
				+ ", totalSelledAmount=" + totalSelledAmount + "]";
	}

}
