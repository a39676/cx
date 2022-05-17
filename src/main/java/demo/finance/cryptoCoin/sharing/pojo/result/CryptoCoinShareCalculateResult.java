package demo.finance.cryptoCoin.sharing.pojo.result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class CryptoCoinShareCalculateResult extends CommonResult {

	private String markDateStr;

	private BigDecimal totalOutput;

	private Integer partingCount;

	private BigDecimal coinCountingOfEachPartOfMachine;

	private BigDecimal hanldingFeeRate;

	private BigDecimal hanldingFee;

	private BigDecimal restAfterHanldingFee;

	private BigDecimal restAfterCommissionFee;

	private BigDecimal netIncome;

	private List<CryptoCoinShareCalculateSubResult> caculateResultList;

	public String getMarkDateStr() {
		return markDateStr;
	}

	public void setMarkDateStr(String markDateStr) {
		this.markDateStr = markDateStr;
	}

	public BigDecimal getTotalOutput() {
		return totalOutput;
	}

	public void setTotalOutput(BigDecimal totalOutput) {
		this.totalOutput = totalOutput;
	}

	public Integer getPartingCount() {
		return partingCount;
	}

	public void setPartingCount(Integer partingCount) {
		this.partingCount = partingCount;
	}

	public BigDecimal getCoinCountingOfEachPartOfMachine() {
		return coinCountingOfEachPartOfMachine;
	}

	public void setCoinCountingOfEachPartOfMachine(BigDecimal coinCountingOfEachPartOfMachine) {
		this.coinCountingOfEachPartOfMachine = coinCountingOfEachPartOfMachine;
	}

	public BigDecimal getHanldingFeeRate() {
		return hanldingFeeRate;
	}

	public void setHanldingFeeRate(BigDecimal hanldingFeeRate) {
		this.hanldingFeeRate = hanldingFeeRate;
	}

	public BigDecimal getHanldingFee() {
		return hanldingFee;
	}

	public void setHanldingFee(BigDecimal hanldingFee) {
		this.hanldingFee = hanldingFee;
	}

	public BigDecimal getRestAfterHanldingFee() {
		return restAfterHanldingFee;
	}

	public void setRestAfterHanldingFee(BigDecimal restAfterHanldingFee) {
		this.restAfterHanldingFee = restAfterHanldingFee;
	}

	public BigDecimal getRestAfterCommissionFee() {
		return restAfterCommissionFee;
	}

	public void setRestAfterCommissionFee(BigDecimal restAfterCommissionFee) {
		this.restAfterCommissionFee = restAfterCommissionFee;
	}

	public BigDecimal getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = netIncome;
	}

	public List<CryptoCoinShareCalculateSubResult> getCaculateResultList() {
		return caculateResultList;
	}

	public void setCaculateResultList(List<CryptoCoinShareCalculateSubResult> caculateResultList) {
		this.caculateResultList = caculateResultList;
	}

	public void addCaculateResult(CryptoCoinShareCalculateSubResult caculateResultList) {
		if (this.caculateResultList == null) {
			this.caculateResultList = new ArrayList<>();
		}
		this.caculateResultList.add(caculateResultList);
	}

	@Override
	public String toString() {
		return "CryptoCoinShareCalculateResult [markDateStr=" + markDateStr + ", totalOutput=" + totalOutput
				+ ", partingCount=" + partingCount + ", coinCountingOfEachPartOfMachine="
				+ coinCountingOfEachPartOfMachine + ", hanldingFeeRate=" + hanldingFeeRate + ", hanldingFee="
				+ hanldingFee + ", restAfterHanldingFee=" + restAfterHanldingFee + ", restAfterCommissionFee="
				+ restAfterCommissionFee + ", netIncome=" + netIncome + ", caculateResultList=" + caculateResultList
				+ "]";
	}

}
