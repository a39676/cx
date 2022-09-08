package demo.finance.cryptoCoin.tool.pojo.dto;

import auxiliaryCommon.pojo.type.TimeUnitType;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;

public class CryptoCoinDataSingleCompareDTO {

	private String startDateTimeStr;

	private String coinTypeOrigin;
	private String coinTypeCompared;

	/** {@link TimeUnitType} */
	private Integer timeUnit;
	private Integer timeRange;

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

	public String getCoinTypeCompared() {
		return coinTypeCompared;
	}

	public void setCoinTypeCompared(String coinTypeCompared) {
		this.coinTypeCompared = coinTypeCompared;
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
		return "CryptoCoinDataSingleCompareDTO [startDateTimeStr=" + startDateTimeStr + ", coinTypeOrigin="
				+ coinTypeOrigin + ", coinTypeCompared=" + coinTypeCompared + ", timeUnit=" + timeUnit + ", timeRange="
				+ timeRange + ", currencyType=" + currencyType + "]";
	}

}
