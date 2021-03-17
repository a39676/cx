package demo.finance.cryptoCoin.notice.pojo.dto;

public class SearchCryptoCoinConditionDTO {

	private String reciverPK;
	private Integer cryptoCoinCode;
	private Integer currencyCode;

	public String getReciverPK() {
		return reciverPK;
	}

	public void setReciverPK(String reciverPK) {
		this.reciverPK = reciverPK;
	}

	public Integer getCryptoCoinCode() {
		return cryptoCoinCode;
	}

	public void setCryptoCoinCode(Integer cryptoCoinCode) {
		this.cryptoCoinCode = cryptoCoinCode;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "SearchCryptoCoinConditionDTO [reciverPK=" + reciverPK + ", cryptoCoinCode=" + cryptoCoinCode
				+ ", currencyCode=" + currencyCode + "]";
	}

}
