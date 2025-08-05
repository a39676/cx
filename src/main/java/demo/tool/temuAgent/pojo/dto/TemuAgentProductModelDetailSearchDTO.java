package demo.tool.temuAgent.pojo.dto;

public class TemuAgentProductModelDetailSearchDTO extends TemuAgentSearchProductDTO {

	private Long productModelId;
	private boolean stockingGreaterThanZero = false;
	private boolean internationalStockingGreaterThanZero = false;

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	public boolean getStockingGreaterThanZero() {
		return stockingGreaterThanZero;
	}

	public void setStockingGreaterThanZero(boolean stockingGreaterThanZero) {
		this.stockingGreaterThanZero = stockingGreaterThanZero;
	}

	public boolean getInternationalStockingGreaterThanZero() {
		return internationalStockingGreaterThanZero;
	}

	public void setInternationalStockingGreaterThanZero(boolean internationalStockingGreaterThanZero) {
		this.internationalStockingGreaterThanZero = internationalStockingGreaterThanZero;
	}

	@Override
	public String toString() {
		return "TemuAgentProductModelDetailSearchDTO [productModelId=" + productModelId + ", stockingGreaterThanZero="
				+ stockingGreaterThanZero + ", internationalStockingGreaterThanZero="
				+ internationalStockingGreaterThanZero + "]";
	}

}
