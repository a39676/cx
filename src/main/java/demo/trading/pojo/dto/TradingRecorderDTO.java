package demo.trading.pojo.dto;

import java.time.LocalDateTime;

import demo.baseCommon.pojo.param.PageParam;

public class TradingRecorderDTO extends PageParam {

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Double minAmount;

	private Double maxAmount;

	private Boolean includeRedCancelOut = false;

	private Long tradingId;

	private Long accountId;

	private Long bankId;

	private Long bankUnionId;

	private String accountNumber;

	private String transationParties;

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Boolean getIncludeRedCancelOut() {
		return includeRedCancelOut;
	}

	public void setIncludeRedCancelOut(Boolean includeRedCancelOut) {
		this.includeRedCancelOut = includeRedCancelOut;
	}

	public Long getTradingId() {
		return tradingId;
	}

	public void setTradingId(Long tradingId) {
		this.tradingId = tradingId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	@Override
	public String toString() {
		return "TradingRecorderDTO [startTime=" + startTime + ", endTime=" + endTime + ", minAmount=" + minAmount
				+ ", maxAmount=" + maxAmount + ", includeRedCancelOut=" + includeRedCancelOut + ", tradingId="
				+ tradingId + ", accountId=" + accountId + ", bankId=" + bankId + ", bankUnionId=" + bankUnionId
				+ ", accountNumber=" + accountNumber + ", transationParties=" + transationParties + ", getPageStart()="
				+ getPageStart() + ", getPageEnd()=" + getPageEnd() + ", getPageSize()=" + getPageSize()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
