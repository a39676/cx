package demo.finance.cryptoCoin.tool.pojo.dto;

import auxiliaryCommon.pojo.type.CurrencyType;
import auxiliaryCommon.pojo.type.TimeUnitType;

public class CryptoCoinDataCompareDTO {

	private String startDateTimeStr;

	/** {@link TimeUnitType} */
	private Integer timeUnit;

	private Integer timeRange;

	private String coinType1;
	private String coinType2;

	/** {@link CurrencyType} */
	private Integer currencyType;

	public Integer getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	public String getCoinType1() {
		return coinType1;
	}

	public void setCoinType1(String coinType1) {
		this.coinType1 = coinType1;
	}

	public String getCoinType2() {
		return coinType2;
	}

	public void setCoinType2(String coinType2) {
		this.coinType2 = coinType2;
	}

	public String getStartDateTimeStr() {
		return startDateTimeStr;
	}

	public void setStartDateTimeStr(String startDateTimeStr) {
		this.startDateTimeStr = startDateTimeStr;
	}

	public Integer getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(Integer timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Integer getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(Integer timeRange) {
		this.timeRange = timeRange;
	}

	@Override
	public String toString() {
		return "CryptoCoinDataCompareDTO [startDateTimeStr=" + startDateTimeStr + ", timeUnit=" + timeUnit
				+ ", timeRange=" + timeRange + ", coinType1=" + coinType1 + ", coinType2=" + coinType2 + "]";
	}

}
