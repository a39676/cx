package demo.trading.pojo.result;

import java.math.BigDecimal;
import java.util.List;

import demo.baseCommon.pojo.result.CommonResult;

public class TradingQueryResult extends CommonResult {

	private List<TradingQuerySubResult> tradingSubResultList;
	private BigDecimal totalAmount;

	public List<TradingQuerySubResult> getTradingSubResultList() {
		return tradingSubResultList;
	}

	public void setTradingSubResultList(List<TradingQuerySubResult> tradingSubResultList) {
		this.tradingSubResultList = tradingSubResultList;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "TradingQueryResult [tradingSubResultList=" + tradingSubResultList + ", totalAmount=" + totalAmount
				+ "]";
	}

}
