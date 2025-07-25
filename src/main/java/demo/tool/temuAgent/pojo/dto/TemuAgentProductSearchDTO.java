package demo.tool.temuAgent.pojo.dto;

public class TemuAgentProductSearchDTO {

	private String productName;
	private Long productId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "TemuAgentProductSearchDTO [productName=" + productName + ", productId=" + productId + "]";
	}

}
