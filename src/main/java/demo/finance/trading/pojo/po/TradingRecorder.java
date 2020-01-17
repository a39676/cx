package demo.finance.trading.pojo.po;

import java.math.BigDecimal;
import java.util.Date;

public class TradingRecorder {
    private Long tradingId;

	private Long accountId;

	private String accountNumber;

	private BigDecimal amount;

	private Date transationDate;

	private String transationParties;

	private Long transationAccountId;

	private Date createTime;

	private String remark;



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
		this.accountNumber = accountNumber == null ? null : accountNumber.trim();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTransationDate() {
		return transationDate;
	}

	public void setTransationDate(Date transationDate) {
		this.transationDate = transationDate;
	}

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties == null ? null : transationParties.trim();
	}

	public Long getTransationAccountId() {
		return transationAccountId;
	}

	public void setTransationAccountId(Long transationAccountId) {
		this.transationAccountId = transationAccountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

}