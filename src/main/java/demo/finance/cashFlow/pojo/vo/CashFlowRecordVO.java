package demo.finance.cashFlow.pojo.vo;

import java.math.BigDecimal;

public class CashFlowRecordVO {

	private BigDecimal flowAmount;

	private Integer currencyCode;
	private String currencyName;

	private Integer timeUnitCode;
	private String timeUnitName;

	private Integer timeCounting;

	public BigDecimal getFlowAmount() {
		return flowAmount;
	}

	public void setFlowAmount(BigDecimal flowAmount) {
		this.flowAmount = flowAmount;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Integer getTimeUnitCode() {
		return timeUnitCode;
	}

	public void setTimeUnitCode(Integer timeUnitCode) {
		this.timeUnitCode = timeUnitCode;
	}

	public String getTimeUnitName() {
		return timeUnitName;
	}

	public void setTimeUnitName(String timeUnitName) {
		this.timeUnitName = timeUnitName;
	}

	public Integer getTimeCounting() {
		return timeCounting;
	}

	public void setTimeCounting(Integer timeCounting) {
		this.timeCounting = timeCounting;
	}

	@Override
	public String toString() {
		return "CashFlowRecordVO [flowAmount=" + flowAmount + ", currencyCode=" + currencyCode + ", currencyName="
				+ currencyName + ", timeUnitCode=" + timeUnitCode + ", timeUnitName=" + timeUnitName + ", timeCounting="
				+ timeCounting + "]";
	}

}
