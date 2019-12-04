package demo.finance.trading.pojo.result;

import java.math.BigDecimal;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class TradingQueryResult extends CommonResult {

	private List<TradingQuerySubResult> tradingSubResultList;
	private BigDecimal totalAmount;
	private BigDecimal totalPay;
	private BigDecimal totalIncome;

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

	public BigDecimal getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(BigDecimal totalPay) {
		this.totalPay = totalPay;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	@Override
	public String toString() {
		return "TradingQueryResult [tradingSubResultList=" + tradingSubResultList + ", totalAmount=" + totalAmount
				+ ", totalPay=" + totalPay + ", totalIncome=" + totalIncome + "]";
	}

}
