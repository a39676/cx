package demo.finance.cryptoCoin.mining.pojo.vo;

import java.math.BigDecimal;

public class CryptoCoinMachineInfoVO {

	private String pk;

	private String machineName;

	private String coinName;

	private BigDecimal handlingFeeRate;

	private Integer partingCount;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
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
		return "CryptoCoinMachineInfoVO [pk=" + pk + ", machineName=" + machineName + ", coinName=" + coinName
				+ ", handlingFeeRate=" + handlingFeeRate + ", partingCount=" + partingCount + "]";
	}

}
