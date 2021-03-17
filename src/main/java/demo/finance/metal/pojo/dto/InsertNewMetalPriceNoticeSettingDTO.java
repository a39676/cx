package demo.finance.metal.pojo.dto;

import java.math.BigDecimal;

public class InsertNewMetalPriceNoticeSettingDTO {

	private Integer metalType;

	private BigDecimal maxGramPrice;

	private BigDecimal minGramPrice;

	private String email;

	private String validTime;

	public Integer getMetalType() {
		return metalType;
	}

	public void setMetalType(Integer metalType) {
		this.metalType = metalType;
	}

	public BigDecimal getMaxGramPrice() {
		return maxGramPrice;
	}

	public void setMaxGramPrice(BigDecimal maxGramPrice) {
		this.maxGramPrice = maxGramPrice;
	}

	public BigDecimal getMinGramPrice() {
		return minGramPrice;
	}

	public void setMinGramPrice(BigDecimal minGramPrice) {
		this.minGramPrice = minGramPrice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	@Override
	public String toString() {
		return "InsertNewMetalPriceNoticeSettingDTO [metalType=" + metalType + ", maxGramPrice=" + maxGramPrice
				+ ", minGramPrice=" + minGramPrice + ", email=" + email + ", validTime=" + validTime + "]";
	}

}
