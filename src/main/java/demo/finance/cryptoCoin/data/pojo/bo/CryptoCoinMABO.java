package demo.finance.cryptoCoin.data.pojo.bo;

public class CryptoCoinMABO implements Comparable<CryptoCoinMABO> {

	private Integer maSize;
	private Double maValue;

	public Integer getMaSize() {
		return maSize;
	}

	public void setMaSize(Integer maSize) {
		this.maSize = maSize;
	}

	public Double getMaValue() {
		return maValue;
	}

	public void setMaValue(Double maValue) {
		this.maValue = maValue;
	}

	@Override
	public String toString() {
		return "CryptoCoinMABO [maSize=" + maSize + ", maValue=" + maValue + "]";
	}

	@Override
	public int compareTo(CryptoCoinMABO o) {
		return compareStartTime(this, o);
	}

	private int compareStartTime(CryptoCoinMABO o, CryptoCoinMABO t) {
		if (o.getMaSize() == null && t.getMaSize() == null) {
			return 0;
		} else if (o.getMaSize() == null) {
			return -1;
		} else if (t.getMaSize() == null) {
			return 1;
		} else {
			if (o.getMaSize() > t.getMaSize()) {
				return 1;
			} else if (o.getMaSize() < t.getMaSize()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
