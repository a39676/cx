package demo.tool.temuAgent.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TemuAgentProductModelStatisticsVO {

	private Long modelId;
	private Integer stockingCounting;
	private LocalDateTime stockingUpdateTime;
	private Integer internationalStockingCounting;
	private LocalDateTime internationalStockingUpdateTime;
	private Integer selledCounting;
	private LocalDateTime selledUpdateTime;

	private String productName;
	private BigDecimal totalStockingCost;

	private BigDecimal avgPrice;
	private BigDecimal highestPrice;
	private BigDecimal lowestPrice;
	private LocalDateTime updateTime;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getTotalStockingCost() {
		return totalStockingCost;
	}

	public void setTotalStockingCost(BigDecimal totalCost) {
		this.totalStockingCost = totalCost;
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

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "TemuAgentProductFlowStatisticsVO [modelId=" + modelId + ", stockingCounting=" + stockingCounting
				+ ", stockingUpdateTime=" + stockingUpdateTime + ", internationalStockingCounting="
				+ internationalStockingCounting + ", internationalStockingUpdateTime=" + internationalStockingUpdateTime
				+ ", selledCounting=" + selledCounting + ", selledUpdateTime=" + selledUpdateTime + ", productName="
				+ productName + ", totalCost=" + totalStockingCost + ", avgPrice=" + avgPrice + ", highestPrice=" + highestPrice
				+ ", lowestPrice=" + lowestPrice + ", updateTime=" + updateTime + "]";
	}

}
