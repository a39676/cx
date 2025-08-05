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
	private BigDecimal declearedPrice;
	private BigDecimal packingFee;
	private LocalDateTime createTime;

	private Integer stockingCounting;
	private LocalDateTime stockingUpdateTime;
	private Integer internationalStockingCounting;
	private LocalDateTime internationalStockingUpdateTime;
	private Integer boughtCounting;
	private LocalDateTime boughtUpdateTime;
	private Integer selledCounting;
	private LocalDateTime selledUpdateTime;
	private Integer repackageCounting;
	private LocalDateTime repackageUdpateTime;

	private String productName;
	private BigDecimal totalCost;
	private BigDecimal totalStockingCost;
	private BigDecimal totalPackingFeeCost;

	private BigDecimal avgBuyPrice;
	private BigDecimal highestBuyPrice;
	private BigDecimal lowestBuyPrice;
	private BigDecimal lastBuyPrice;
	private BigDecimal avgSellPrice;
	private BigDecimal highestSellPrice;
	private BigDecimal lowestSellPrice;
	private BigDecimal lastSellPrice;

	private BigDecimal totalSelledAmount;
	private String createTimeStr;

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

	public Integer getBoughtCounting() {
		return boughtCounting;
	}

	public void setBoughtCounting(Integer boughtCounting) {
		this.boughtCounting = boughtCounting;
	}

	public LocalDateTime getBoughtUpdateTime() {
		return boughtUpdateTime;
	}

	public void setBoughtUpdateTime(LocalDateTime boughtUpdateTime) {
		this.boughtUpdateTime = boughtUpdateTime;
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

	public BigDecimal getTotalStockingCost() {
		return totalStockingCost;
	}

	public void setTotalStockingCost(BigDecimal totalStockingCost) {
		this.totalStockingCost = totalStockingCost;
	}

	public BigDecimal getTotalPackingFeeCost() {
		return totalPackingFeeCost;
	}

	public void setTotalPackingFeeCost(BigDecimal totalPackingFeeCost) {
		this.totalPackingFeeCost = totalPackingFeeCost;
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

	public BigDecimal getLastBuyPrice() {
		return lastBuyPrice;
	}

	public void setLastBuyPrice(BigDecimal lastBuyPrice) {
		this.lastBuyPrice = lastBuyPrice;
	}

	public void setLowestBuyPrice(BigDecimal lowestBuyPrice) {
		this.lowestBuyPrice = lowestBuyPrice;
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

	public BigDecimal getTotalSelledAmount() {
		return totalSelledAmount;
	}

	public void setTotalSelledAmount(BigDecimal totalSelledAmount) {
		this.totalSelledAmount = totalSelledAmount;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	@Override
	public String toString() {
		return "TemuAgentProductModelStatisticsVO [modelId=" + modelId + ", productId=" + productId + ", unitTypeCode="
				+ unitTypeCode + ", unitTypeName=" + unitTypeName + ", unitCounting=" + unitCounting + ", spu=" + spu
				+ ", sku=" + sku + ", skc=" + skc + ", declearedPrice=" + declearedPrice + ", packingFee=" + packingFee
				+ ", createTime=" + createTime + ", stockingCounting=" + stockingCounting + ", stockingUpdateTime="
				+ stockingUpdateTime + ", internationalStockingCounting=" + internationalStockingCounting
				+ ", internationalStockingUpdateTime=" + internationalStockingUpdateTime + ", boughtCounting="
				+ boughtCounting + ", boughtUpdateTime=" + boughtUpdateTime + ", selledCounting=" + selledCounting
				+ ", selledUpdateTime=" + selledUpdateTime + ", repackageCounting=" + repackageCounting
				+ ", repackageUdpateTime=" + repackageUdpateTime + ", productName=" + productName + ", totalCost="
				+ totalCost + ", totalStockingCost=" + totalStockingCost + ", totalPackingFeeCost="
				+ totalPackingFeeCost + ", avgBuyPrice=" + avgBuyPrice + ", highestBuyPrice=" + highestBuyPrice
				+ ", lowestBuyPrice=" + lowestBuyPrice + ", lastBuyPrice=" + lastBuyPrice + ", avgSellPrice="
				+ avgSellPrice + ", highestSellPrice=" + highestSellPrice + ", lowestSellPrice=" + lowestSellPrice
				+ ", lastSellPrice=" + lastSellPrice + ", totalSelledAmount=" + totalSelledAmount + ", createTimeStr="
				+ createTimeStr + "]";
	}

}
