package demo.finance.account_info.pojo.result;

import java.math.BigDecimal;

import auxiliaryCommon.pojo.result.CommonResult;

public class InsertTransationResult extends CommonResult {

	private String transationParties;

	private BigDecimal transationAmount;

	private String transationDate;

	private String accountNumber;

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties;
	}

	public BigDecimal getTransationAmount() {
		return transationAmount;
	}

	public void setTransationAmount(BigDecimal transationAmount) {
		this.transationAmount = transationAmount;
	}

	public String getTransationDate() {
		return transationDate;
	}

	public void setTransationDate(String transationDate) {
		this.transationDate = transationDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "InsertTransationResult [transationParties=" + transationParties + ", transationAmount="
				+ transationAmount + ", transationDate=" + transationDate + ", accountNumber=" + accountNumber + "]";
	}


}
