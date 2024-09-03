package demo.finance.cryptoCoin.data.pojo.dto;

import java.math.BigDecimal;

public class CryptoCoinForceOrderSummaryDTO {

	private String symbol;
	private BigDecimal forceBuy = BigDecimal.ZERO;
	private BigDecimal forceSell = BigDecimal.ZERO;
	private Integer forceBuyCounting = 0;
	private Integer forceSellCounting = 0;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getForceBuy() {
		return forceBuy;
	}

	public void setForceBuy(BigDecimal forceBuy) {
		this.forceBuy = forceBuy;
	}

	public BigDecimal getForceSell() {
		return forceSell;
	}

	public void setForceSell(BigDecimal forceSell) {
		this.forceSell = forceSell;
	}

	public Integer getForceBuyCounting() {
		return forceBuyCounting;
	}

	public void setForceBuyCounting(Integer forceBuyCounting) {
		this.forceBuyCounting = forceBuyCounting;
	}

	public Integer getForceSellCounting() {
		return forceSellCounting;
	}

	public void setForceSellCounting(Integer forceSellCounting) {
		this.forceSellCounting = forceSellCounting;
	}

	@Override
	public String toString() {
		return "CryptoCoinForceOrderSummaryDTO [symbol=" + symbol + ", forceBuy=" + forceBuy + ", forceSell="
				+ forceSell + ", forceBuyCounting=" + forceBuyCounting + ", forceSellCounting=" + forceSellCounting
				+ "]";
	}

}
