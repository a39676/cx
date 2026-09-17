package demo.tool.other.pojo.result;

import java.math.BigDecimal;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

/**
 * 
 */
public class MeituanOrderHtmlEditResult extends CommonResult {

	private List<BigDecimal> priceList;
	private BigDecimal totalPrice;
	private String htmlStr;

	public List<BigDecimal> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<BigDecimal> priceList) {
		this.priceList = priceList;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getHtmlStr() {
		return htmlStr;
	}

	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}

	@Override
	public String toString() {
		return "MeituanOrderHtmlEditResult [priceList=" + priceList + ", totalPrice=" + totalPrice + ", htmlStr="
				+ htmlStr + "]";
	}

}
