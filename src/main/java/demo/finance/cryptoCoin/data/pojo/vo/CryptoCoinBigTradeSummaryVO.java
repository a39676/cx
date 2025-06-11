package demo.finance.cryptoCoin.data.pojo.vo;

import java.math.BigDecimal;

public class CryptoCoinBigTradeSummaryVO implements Comparable<CryptoCoinBigTradeSummaryVO> {

	private String symbol;
	private BigDecimal amountBuy = BigDecimal.ZERO;
	private BigDecimal amountSell = BigDecimal.ZERO;
	private BigDecimal amountTotal = BigDecimal.ZERO;
	private Integer countingBuy = 0;
	private Integer countingSell = 0;
	private Integer countingTotal = 0;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getAmountBuy() {
		return amountBuy;
	}

	public void setAmountBuy(BigDecimal amountBuy) {
		this.amountBuy = amountBuy;
	}

	public BigDecimal getAmountSell() {
		return amountSell;
	}

	public void setAmountSell(BigDecimal amountSell) {
		this.amountSell = amountSell;
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Integer getCountingBuy() {
		return countingBuy;
	}

	public void setCountingBuy(Integer countingBuy) {
		this.countingBuy = countingBuy;
	}

	public Integer getCountingSell() {
		return countingSell;
	}

	public void setCountingSell(Integer countingSell) {
		this.countingSell = countingSell;
	}

	public Integer getCountingTotal() {
		return countingTotal;
	}

	public void setCountingTotal(Integer countingTotal) {
		this.countingTotal = countingTotal;
	}

	@Override
	public String toString() {
		return "CryptoCoinBigTradeSummaryVO [symbol=" + symbol + ", amountBuy=" + amountBuy + ", amountSell="
				+ amountSell + ", amountTotal=" + amountTotal + ", countingBuy=" + countingBuy + ", countingSell="
				+ countingSell + ", countingTotal=" + countingTotal + "]";
	}

	@Override
	public int compareTo(CryptoCoinBigTradeSummaryVO o) {
		return compareByAmountAndCounting(this, o);
	}

	private int compareByAmountAndCounting(CryptoCoinBigTradeSummaryVO t, CryptoCoinBigTradeSummaryVO o) {
		int compareByAmount = compareByAmount(t, o);
		if (compareByAmount != 0) {
			return compareByAmount;
		}
		return compareByCounting(t, o);
	}

	private int compareByAmount(CryptoCoinBigTradeSummaryVO t, CryptoCoinBigTradeSummaryVO o) {
		if (t.getAmountTotal() == null && o.getAmountTotal() == null) {
			return 0;
		}
		if (t.getAmountTotal() == null) {
			return -1;
		} else if (o.getAmountTotal() == null) {
			return 1;
		}
		return t.getAmountTotal().compareTo(o.getAmountTotal());
	}

	private int compareByCounting(CryptoCoinBigTradeSummaryVO t, CryptoCoinBigTradeSummaryVO o) {
		if (t.getCountingTotal() == null && o.getCountingTotal() == null) {
			return 0;
		}
		if (t.getCountingTotal() == null) {
			return -1;
		} else if (o.getCountingTotal() == null) {
			return 1;
		}
		if (t.getCountingTotal() > o.getCountingTotal()) {
			return 1;
		} else if (t.getCountingTotal() == o.getCountingTotal()) {
			return 0;
		} else {
			return -1;
		}
	}

}
