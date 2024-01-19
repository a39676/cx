package demo.finance.cashFlow.pojo.bo;

public class CashFlowSubSummaryBO {

	private Double dailyFlow = 0D;
	private Double monthlyFlow = 0D;
	private Double annualFlow = 0D;

	public Double getDailyFlow() {
		return dailyFlow;
	}

	public void setDailyFlow(Double dailyFlow) {
		this.dailyFlow = dailyFlow;
	}

	public Double getMonthlyFlow() {
		return monthlyFlow;
	}

	public void setMonthlyFlow(Double monthlyFlow) {
		this.monthlyFlow = monthlyFlow;
	}

	public Double getAnnualFlow() {
		return annualFlow;
	}

	public void setAnnualFlow(Double annualFlow) {
		this.annualFlow = annualFlow;
	}

	@Override
	public String toString() {
		return "CashFlowSubSummaryBO [dailyFlow=" + dailyFlow + ", monthlyFlow=" + monthlyFlow + ", annualFlow="
				+ annualFlow + "]";
	}

}
