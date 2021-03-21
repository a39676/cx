package demo.finance.cryptoCoin.notice.pojo.dto;

public class SearchCryptoCoinConditionDTO {

	private String reciverPK;
	private String cryptoCoinType;
	private Integer currencyCode;

	public String getReciverPK() {
		return reciverPK;
	}

	public void setReciverPK(String reciverPK) {
		this.reciverPK = reciverPK;
	}

	public String getCryptoCoinType() {
		return cryptoCoinType;
	}

	public void setCryptoCoinType(String cryptoCoinType) {
		this.cryptoCoinType = cryptoCoinType;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "SearchCryptoCoinConditionDTO [reciverPK=" + reciverPK + ", cryptoCoinType=" + cryptoCoinType
				+ ", currencyCode=" + currencyCode + "]";
	}

}
