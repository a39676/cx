package demo.finance.cryptoCoin.tool.pojo.result;

import java.math.BigDecimal;

import auxiliaryCommon.pojo.result.CommonResult;

public class CryptoDataCompareRateSubResult extends CommonResult implements Comparable<CryptoDataCompareRateSubResult> {

	private String originCoinTypeName;
	private String comparedCoinTypeName;
	private BigDecimal differentRate;

	public String getOriginCoinTypeName() {
		return originCoinTypeName;
	}

	public void setOriginCoinTypeName(String originCoinTypeName) {
		this.originCoinTypeName = originCoinTypeName;
	}

	public String getComparedCoinTypeName() {
		return comparedCoinTypeName;
	}

	public void setComparedCoinTypeName(String comparedCoinTypeName) {
		this.comparedCoinTypeName = comparedCoinTypeName;
	}

	public BigDecimal getDifferentRate() {
		return differentRate;
	}

	public void setDifferentRate(BigDecimal differentRate) {
		this.differentRate = differentRate;
	}

	@Override
	public String toString() {
		return "CryptoDataCompareRateSubResult [originCoinTypeName=" + originCoinTypeName + ", comparedCoinTypeName="
				+ comparedCoinTypeName + ", differentRate=" + differentRate + "]";
	}

	@Override
	public int compareTo(CryptoDataCompareRateSubResult o) {
		return compareStartTime(o, this);
	}

	private int compareStartTime(CryptoDataCompareRateSubResult o, CryptoDataCompareRateSubResult t) {
		if (o.getDifferentRate() == null && t.getDifferentRate() == null) {
			return 0;
		} else if (o.getDifferentRate() == null) {
			return -1;
		} else if (this.getDifferentRate() == null) {
			return 1;
		} else {
			return o.getDifferentRate().compareTo(this.getDifferentRate());
		}
	}

}
