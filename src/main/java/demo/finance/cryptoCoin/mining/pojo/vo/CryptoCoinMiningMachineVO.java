package demo.finance.cryptoCoin.mining.pojo.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CryptoCoinMiningMachineVO implements Comparable<CryptoCoinMiningMachineVO> {

	private Long id;
	private String idStr;
	private String machineName;
	private String coinIdStr;
	private Long coinId;
	private String coinName;
	private BigDecimal handlingFeeRate;
	private Integer partingCount;
	private List<AllocationAssistantVO> assistantList;

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
		return "CryptoCoinMiningMachineVO [id=" + id + ", idStr=" + idStr + ", machineName=" + machineName
				+ ", coinIdStr=" + coinIdStr + ", coinId=" + coinId + ", coinName=" + coinName + ", handlingFeeRate="
				+ handlingFeeRate + ", partingCount=" + partingCount + ", assistantList=" + assistantList + "]";
	}

	@Override
	public int compareTo(CryptoCoinMiningMachineVO o) {
		return compareId(o, this);
	}

	private int compareId(CryptoCoinMiningMachineVO o, CryptoCoinMiningMachineVO t) {
		if (o.getId() == null || t.getId() == null) {
			if (o.getId() == null && t.getId() == null) {
				return 0;
			} else if (o.getId() == null) {
				return 1;
			} else if (t.getId() == null) {
				return -1;
			} else {
				return 0;
			}
		} else {
			if (t.getId() > (o.getId())) {
				return 1;
			} else if (t.getId() < (o.getId())) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
