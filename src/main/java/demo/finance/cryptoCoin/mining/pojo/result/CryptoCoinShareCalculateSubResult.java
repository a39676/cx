package demo.finance.cryptoCoin.mining.pojo.result;

import java.math.BigDecimal;

public class CryptoCoinShareCalculateSubResult {

	private Long assistantId;
	private String assistantName;
	private BigDecimal partingCount;
	private BigDecimal receiveFromParting;
	private BigDecimal commissionFee;
	private BigDecimal commissionFeeRate;
	private BigDecimal totalCoinCounting;

	public Long getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(Long assistantId) {
		this.assistantId = assistantId;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public BigDecimal getPartingCount() {
		return partingCount;
	}

	public void setPartingCount(BigDecimal partingCount) {
		this.partingCount = partingCount;
	}

	public BigDecimal getTotalCoinCounting() {
		return totalCoinCounting;
	}

	public void setTotalCoinCounting(BigDecimal totalCoinCounting) {
		this.totalCoinCounting = totalCoinCounting;
	}

	public BigDecimal getCommissionFee() {
		return commissionFee;
	}

	public void setCommissionFee(BigDecimal commissionFee) {
		this.commissionFee = commissionFee;
	}

	public BigDecimal getCommissionFeeRate() {
		return commissionFeeRate;
	}

	public void setCommissionFeeRate(BigDecimal commissionFeeRate) {
		this.commissionFeeRate = commissionFeeRate;
	}

	public BigDecimal getReceiveFromParting() {
		return receiveFromParting;
	}

	public void setReceiveFromParting(BigDecimal receiveFromParting) {
		this.receiveFromParting = receiveFromParting;
	}

	@Override
	public String toString() {
		return "CryptoCoinShareCalculateSubResult [assistantId=" + assistantId + ", assistantName=" + assistantName
				+ ", partingCount=" + partingCount + ", receiveFromParting=" + receiveFromParting + ", commissionFee="
				+ commissionFee + ", commissionFeeRate=" + commissionFeeRate + ", totalCoinCounting="
				+ totalCoinCounting + "]";
	}

}
