package demo.tool.temuAgent.pojo.dto;

import java.math.BigDecimal;

public class TemuAgentCeateProductDTO {

	private String productName;
	private String releaseDataStr;
	private BigDecimal unitPrice;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReleaseDataStr() {
		return releaseDataStr;
	}

	public void setReleaseDataStr(String releaseDataStr) {
		this.releaseDataStr = releaseDataStr;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "TemuAgentCeateProductDTO [productName=" + productName + ", releaseDataStr=" + releaseDataStr
				+ ", unitPrice=" + unitPrice + "]";
	}

}
