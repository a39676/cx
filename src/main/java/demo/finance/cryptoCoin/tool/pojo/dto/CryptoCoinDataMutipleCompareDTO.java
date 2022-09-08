package demo.finance.cryptoCoin.tool.pojo.dto;

import java.util.List;

import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

public class CryptoCoinDataMutipleCompareDTO {

	private String startDateTimeStr;

	private String coinTypeOrigin;
	private List<String> coinTypeComparedList;

	/** {@link CurrencyTypeForCryptoCoin} */
	private Integer currencyType;

	public Integer getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	public String getCoinTypeOrigin() {
		return coinTypeOrigin;
	}

	public void setCoinTypeOrigin(String coinTypeSource) {
		this.coinTypeOrigin = coinTypeSource;
	}

	public List<String> getCoinTypeComparedList() {
		return coinTypeComparedList;
	}

	public void setCoinTypeComparedList(List<String> coinTypeComparedList) {
		this.coinTypeComparedList = coinTypeComparedList;
	}

	public String getStartDateTimeStr() {
		return startDateTimeStr;
	}

	public void setStartDateTimeStr(String startDateTimeStr) {
		this.startDateTimeStr = startDateTimeStr;
	}

	@Override
	public String toString() {
		return "CryptoCoinDataMutipleCompareDTO [startDateTimeStr=" + startDateTimeStr + ", coinTypeOrigin="
				+ coinTypeOrigin + ", coinTypeComparedList=" + coinTypeComparedList + ", currencyType=" + currencyType
				+ "]";
	}

}
