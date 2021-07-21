package demo.finance.cryptoCoin.tool.pojo.dto;

public class CryptoCoinLowPriceCompareDTO implements Comparable<CryptoCoinLowPriceCompareDTO>{

	private String shortNameEN;
	private Double rate;

	public String getShortNameEN() {
		return shortNameEN;
	}

	public void setShortNameEN(String shortNameEN) {
		this.shortNameEN = shortNameEN;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "CryptoCoinLowPriceCompareDTO [shortNameEN=" + shortNameEN + ", rate=" + rate + "]";
	}

	@Override
	public int compareTo(CryptoCoinLowPriceCompareDTO o) {
		return compareRate(this, o);
	}

	private int compareRate(CryptoCoinLowPriceCompareDTO o, CryptoCoinLowPriceCompareDTO t) {
		if (o.getRate() == null || t.getRate() == null) {
			if (o.getRate() == null && t.getRate() == null) {
				return 0;
			} else if (o.getRate() == null) {
				return 1;
			} else if (t.getRate() == null) {
				return -1;
			} else {
				return 0;
			}
		} else {
			if (o.getRate() < t.getRate()) {
				return 1;
			} else if (o.getRate() > t.getRate()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
