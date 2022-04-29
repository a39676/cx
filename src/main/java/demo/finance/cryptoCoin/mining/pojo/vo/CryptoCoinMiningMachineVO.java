package demo.finance.cryptoCoin.mining.pojo.vo;

import java.math.BigDecimal;

public class CryptoCoinMiningMachineVO {

	private String id;
	private String machineName;
	private String coinId;
	private String coinName;
	private BigDecimal handlingFeeRate;
	private Integer partingCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
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

	@Override
	public String toString() {
		return "CryptoCoinMiningMachineVO [id=" + id + ", machineName=" + machineName + ", coinId=" + coinId
				+ ", coinName=" + coinName + ", handlingFeeRate=" + handlingFeeRate + ", partingCount=" + partingCount
				+ "]";
	}

}
