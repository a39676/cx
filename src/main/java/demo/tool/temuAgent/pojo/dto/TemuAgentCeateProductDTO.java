package demo.tool.temuAgent.pojo.dto;

public class TemuAgentCeateProductDTO {

	private String productName;
	private String releaseDateStr;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

	@Override
	public String toString() {
		return "TemuAgentCeateProductDTO [productName=" + productName + ", releaseDateStr=" + releaseDateStr + "]";
	}

}
