package demo.finance.cryptoCoin.mining.pojo.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CryptoCoinMiningMachineVO {

	private String machinePK;
	private String machineName;
	private String coinIdStr;
	private Long coinId;
	private String coinName;
	private BigDecimal handlingFeeRate;
	private Integer partingCount;
	private List<AllocationAssistantVO> assistantList;

	public String getMachinePK() {
		return machinePK;
	}

	public void setMachinePK(String machinePK) {
		this.machinePK = machinePK;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getCoinIdStr() {
		return coinIdStr;
	}

	public void setCoinIdStr(String coinIdStr) {
		this.coinIdStr = coinIdStr;
	}

	public Long getCoinId() {
		return coinId;
	}

	public void setCoinId(Long coinId) {
		this.coinId = coinId;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public BigDecimal getHandlingFeeRate() {
		return handlingFeeRate;
	}

	public void setHandlingFeeRate(BigDecimal handlingFeeRate) {
		this.handlingFeeRate = handlingFeeRate;
	}

	public Integer getPartingCount() {
		return partingCount;
	}

	public void setPartingCount(Integer partingCount) {
		this.partingCount = partingCount;
	}

	public List<AllocationAssistantVO> getAssistantList() {
		return assistantList;
	}

	public List<AllocationAssistantVO> addAssistant(AllocationAssistantVO vo) {
		if (assistantList == null) {
			assistantList = new ArrayList<>();
		}
		assistantList.add(vo);
		return assistantList;
	}

	public void setAssistantList(List<AllocationAssistantVO> assistantList) {
		this.assistantList = assistantList;
	}

	@Override
	public String toString() {
		return "CryptoCoinMiningMachineVO [machinePK=" + machinePK + ", machineName=" + machineName + ", coinIdStr="
				+ coinIdStr + ", coinId=" + coinId + ", coinName=" + coinName + ", handlingFeeRate=" + handlingFeeRate
				+ ", partingCount=" + partingCount + ", assistantList=" + assistantList + "]";
	}

}
