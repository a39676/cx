package demo.finance.cnStockMarket.pojo.dto;

public class CnStockMarketNoticeSettingDTO {

	private String stockCode;
	private Double maxPrice;
	private Double minPrice;
	private boolean noticed = false;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public boolean getNoticed() {
		return noticed;
	}

	public void setNoticed(boolean noticed) {
		this.noticed = noticed;
	}

	@Override
	public String toString() {
		return "CnStockMarketNoticeSettingDTO [stockCode=" + stockCode + ", maxPrice=" + maxPrice + ", minPrice="
				+ minPrice + ", noticed=" + noticed + "]";
	}

}
