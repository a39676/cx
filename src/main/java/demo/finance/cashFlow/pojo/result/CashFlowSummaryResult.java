package demo.finance.cashFlow.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cashFlow.pojo.vo.CashFlowRecordVO;

public class CashFlowSummaryResult extends CommonResult {

	private Double dailyFlowSummary;
	private Double monthlyFlowSummary;
	private Double annualFlowSummary;

	private Double annualSummary;
	private Double annualIncomeSummary;
	private Double annualConsumeSummary;

	private List<CashFlowRecordVO> recordList;

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

	public Double getAnnualSummary() {
		return annualSummary;
	}

	public void setAnnualSummary(Double annualSummary) {
		this.annualSummary = annualSummary;
	}

	public Double getAnnualIncomeSummary() {
		return annualIncomeSummary;
	}

	public void setAnnualIncomeSummary(Double annualIncomeSummary) {
		this.annualIncomeSummary = annualIncomeSummary;
	}

	public Double getAnnualConsumeSummary() {
		return annualConsumeSummary;
	}

	public void setAnnualConsumeSummary(Double annualConsumeSummary) {
		this.annualConsumeSummary = annualConsumeSummary;
	}

	public List<CashFlowRecordVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<CashFlowRecordVO> recordList) {
		this.recordList = recordList;
	}

	@Override
	public String toString() {
		return "CashFlowSummaryResult [dailyFlowSummary=" + dailyFlowSummary + ", monthlyFlowSummary="
				+ monthlyFlowSummary + ", annualFlowSummary=" + annualFlowSummary + ", annualSummary=" + annualSummary
				+ ", annualIncomeSummary=" + annualIncomeSummary + ", annualConsumeSummary=" + annualConsumeSummary
				+ ", recordList=" + recordList + "]";
	}

}
