package demo.finance.cashFlow.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class CashFlowSummaryResult extends CommonResult {

	private Double dailyFlowSummary;
	private Double monthlyFlowSummary;
	private Double annualFlowSummary;

	private Double annualTotal;

	public Double getDailyFlowSummary() {
		return dailyFlowSummary;
	}

	public void setDailyFlowSummary(Double dailyFlowSummary) {
		this.dailyFlowSummary = dailyFlowSummary;
	}

	public Double getMonthlyFlowSummary() {
		return monthlyFlowSummary;
	}

	public void setMonthlyFlowSummary(Double monthlyFlowSummary) {
		this.monthlyFlowSummary = monthlyFlowSummary;
	}

	public Double getAnnualFlowSummary() {
		return annualFlowSummary;
	}

	public void setAnnualFlowSummary(Double annualFlowSummary) {
		this.annualFlowSummary = annualFlowSummary;
	}

	public Double getAnnualTotal() {
		return annualTotal;
	}

	public void setAnnualTotal(Double annualTotal) {
		this.annualTotal = annualTotal;
	}

	@Override
	public String toString() {
		return "CashFlowSummaryResult [dailyFlowSummary=" + dailyFlowSummary + ", monthlyFlowSummary="
				+ monthlyFlowSummary + ", annualFlowSummary=" + annualFlowSummary + ", annualTotal=" + annualTotal
				+ "]";
	}

}
