package demo.finance.cryptoCoin.mining.pojo.result;

import java.math.BigDecimal;

public class CryptoCoinShareCalculateSubResult {

	private Long assistantId;
	private String assistantName;
	private BigDecimal coinCounting;
	private BigDecimal commissionFee;

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

	public BigDecimal getCoinCounting() {
		return coinCounting;
	}

	public void setCoinCounting(BigDecimal coinCounting) {
		this.coinCounting = coinCounting;
	}

	public BigDecimal getCommissionFee() {
		return commissionFee;
	}

	public void setCommissionFee(BigDecimal commissionFee) {
		this.commissionFee = commissionFee;
	}

	@Override
	public String toString() {
		return "CryptoCoinShareCalculateSubResult [assistantId=" + assistantId + ", assistantName=" + assistantName
				+ ", coinCounting=" + coinCounting + ", commissionFee=" + commissionFee + "]";
	}

}
