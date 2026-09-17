package demo.tool.other.pojo.dto;

import java.math.BigDecimal;

public class MeituanOrderHtmlEditDTO {

	private BigDecimal rate;
	private String htmlStr;

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getHtmlStr() {
		return htmlStr;
	}

	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}

	@Override
	public String toString() {
		return "MeituanOrderHtmlEditDTO [rate=" + rate + ", htmlStr=" + htmlStr + "]";
	}

}
