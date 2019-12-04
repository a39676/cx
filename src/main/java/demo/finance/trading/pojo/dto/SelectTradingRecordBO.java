package demo.finance.trading.pojo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SelectTradingRecordBO {

	private Long accountId;
	private List<Long> accountIdList;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private BigDecimal minAmount;
	private BigDecimal maxAmount;
	private Boolean includeRedCancelOut;
	private String remark;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public List<Long> getAccountIdList() {
		return accountIdList;
	}

	public void setAccountIdList(List<Long> accountIdList) {
		this.accountIdList = accountIdList;
	}

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

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Boolean getIncludeRedCancelOut() {
		return includeRedCancelOut;
	}

	public void setIncludeRedCancelOut(Boolean includeRedCancelOut) {
		this.includeRedCancelOut = includeRedCancelOut;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SelectTradingRecordBO [accountId=" + accountId + ", accountIdList=" + accountIdList + ", startTime="
				+ startTime + ", endTime=" + endTime + ", minAmount=" + minAmount + ", maxAmount=" + maxAmount
				+ ", includeRedCancelOut=" + includeRedCancelOut + ", remark=" + remark + "]";
	}

}
