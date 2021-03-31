package demo.finance.cryptoCoin.data.pojo.bo;

public class CryptoCoinMABO {

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

}
