package demo.finance.cryptoCoin.sharing.pojo.vo;

import java.math.BigDecimal;

public class AllocationAssistantVO {

	private String pk;
	private String name;
	private Long machineId;
	private String machineIdStr;
	private BigDecimal partingCount;
	private BigDecimal commissionFeeRate;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	public String getMachineIdStr() {
		return machineIdStr;
	}

	public void setMachineIdStr(String machineIdStr) {
		this.machineIdStr = machineIdStr;
	}

	public BigDecimal getPartingCount() {
		return partingCount;
	}

	public void setPartingCount(BigDecimal partingCount) {
		this.partingCount = partingCount;
	}

	public BigDecimal getCommissionFeeRate() {
		return commissionFeeRate;
	}

	public void setCommissionFeeRate(BigDecimal commissionFeeRate) {
		this.commissionFeeRate = commissionFeeRate;
	}

	@Override
	public String toString() {
		return "AllocationAssistantVO [pk=" + pk + ", name=" + name + ", machineId=" + machineId + ", machineIdStr="
				+ machineIdStr + ", partingCount=" + partingCount + ", commissionFeeRate=" + commissionFeeRate + "]";
	}

}
