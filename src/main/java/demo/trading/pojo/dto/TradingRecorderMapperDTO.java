package demo.trading.pojo.dto;

import java.time.LocalDateTime;
import java.util.List;

import demo.baseCommon.pojo.param.PageDTO;

public class TradingRecorderMapperDTO extends PageDTO {

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Double minAmount;

	private Double maxAmount;

	private Boolean includeRedCancelOut = false;

	private Long tradingId;

	private Long accountId;

	private String accountNumber;

	private List<Long> accountIdList;

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


	public List<Long> getAccountIdList() {
		return accountIdList;
	}

	public void setAccountIdList(List<Long> accountIdList) {
		this.accountIdList = accountIdList;
	}

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties;
	}

	@Override
	public String toString() {
		return "TradingRecorderMapperDTO [startTime=" + startTime + ", endTime=" + endTime + ", minAmount=" + minAmount
				+ ", maxAmount=" + maxAmount + ", includeRedCancelOut=" + includeRedCancelOut + ", tradingId="
				+ tradingId + ", accountId=" + accountId + ", accountNumber=" + accountNumber + ", accountIdList="
				+ accountIdList + ", transationParties=" + transationParties + ", getPageNo()=" + getPageNo()
				+ ", getPageSize()=" + getPageSize() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
