package demo.tool.temuAgent.pojo.dto;

public class TemuAgentProductModelDetailSearchDTO extends TemuAgentSearchProductDTO {

	private Long productModelId;

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	@Override
	public String toString() {
		return "TemuAgentProductModelDetailSearchDTO [productModelId=" + productModelId + "]";
	}

}
