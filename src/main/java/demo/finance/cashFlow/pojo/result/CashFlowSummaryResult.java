package demo.finance.cashFlow.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cashFlow.pojo.vo.CashFlowRecordVO;

public class CashFlowSummaryResult extends CommonResult {

	private Double dailyFlowSummary;
	private Double monthlyFlowSummary;
	private Double annualFlowSummary;

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

	public List<CashFlowRecordVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<CashFlowRecordVO> recordList) {
		this.recordList = recordList;
	}

	@Override
	public String toString() {
		return "CashFlowSummaryResult [dailyFlowSummary=" + dailyFlowSummary + ", monthlyFlowSummary="
				+ monthlyFlowSummary + ", annualFlowSummary=" + annualFlowSummary + ", recordList=" + recordList + "]";
	}

}
