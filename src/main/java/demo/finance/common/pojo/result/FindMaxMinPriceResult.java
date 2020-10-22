package demo.finance.common.pojo.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import auxiliaryCommon.pojo.result.CommonResult;

public class FindMaxMinPriceResult extends CommonResult {
	private BigDecimal maxPrice;
	private BigDecimal minPrice;

	private LocalDateTime maxPriceDateTime;
	private LocalDateTime minPriceDateTime;

	public LocalDateTime getMaxPriceDateTime() {
		return maxPriceDateTime;
	}

	public void setMaxPriceDateTime(LocalDateTime maxPriceDateTime) {
		this.maxPriceDateTime = maxPriceDateTime;
	}

	public LocalDateTime getMinPriceDateTime() {
		return minPriceDateTime;
	}

	public void setMinPriceDateTime(LocalDateTime minPriceDateTime) {
		this.minPriceDateTime = minPriceDateTime;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	@Override
	public String toString() {
		return "FindMaxMinPriceResult [maxPrice=" + maxPrice + ", minPrice=" + minPrice + ", maxPriceDateTime="
				+ maxPriceDateTime + ", minPriceDateTime=" + minPriceDateTime + "]";
	}

}
