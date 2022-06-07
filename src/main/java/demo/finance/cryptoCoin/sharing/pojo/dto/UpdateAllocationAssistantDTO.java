package demo.finance.cryptoCoin.sharing.pojo.dto;

import java.math.BigDecimal;

public class UpdateAllocationAssistantDTO {

	private String assistantPK;

	private String machinePK;

	private BigDecimal partingCount;

	private BigDecimal commissionFeeRateInPercent;

	public String getAssistantPK() {
		return assistantPK;
	}

	public void setAssistantPK(String assistantPK) {
		this.assistantPK = assistantPK;
	}

	public String getMachinePK() {
		return machinePK;
	}

	public void setMachinePK(String machinePK) {
		this.machinePK = machinePK;
	}

	public BigDecimal getPartingCount() {
		return partingCount;
	}

	public void setPartingCount(BigDecimal partingCount) {
		this.partingCount = partingCount;
	}

	public BigDecimal getCommissionFeeRateInPercent() {
		return commissionFeeRateInPercent;
	}

	public void setCommissionFeeRateInPercent(BigDecimal commissionFeeRateInPercent) {
		this.commissionFeeRateInPercent = commissionFeeRateInPercent;
	}

	@Override
	public String toString() {
		return "UpdateAllocationAssistantDTO [assistantPK=" + assistantPK + ", machinePK=" + machinePK
				+ ", partingCount=" + partingCount + ", commissionFeeRateInPercent=" + commissionFeeRateInPercent + "]";
	}

}
