package demo.tool.temuAgent.pojo.dto;

import java.math.BigDecimal;

public class TemuAgentCeateProductDTO {

	private String productName;
	private BigDecimal unitPrice;
	private String releaseDateStr;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

	@Override
	public String toString() {
		return "TemuAgentCeateProductDTO [productName=" + productName + ", unitPrice=" + unitPrice + ", releaseDateStr="
				+ releaseDateStr + "]";
	}

}
