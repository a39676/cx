package demo.finance.cryptoCoin.mining.pojo.vo;

import java.math.BigDecimal;

public class AllocationAssistantVO {

	private Long id;
	private String idStr;
	private String name;
	private Long machineId;
	private String machineIdStr;
	private BigDecimal partingCount;
	private BigDecimal commissionFeeRate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
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
		return "AllocationAssistantVO [id=" + id + ", idStr=" + idStr + ", name=" + name + ", machineId=" + machineId
				+ ", machineIdStr=" + machineIdStr + ", partingCount=" + partingCount + ", commissionFeeRate="
				+ commissionFeeRate + "]";
	}

}
