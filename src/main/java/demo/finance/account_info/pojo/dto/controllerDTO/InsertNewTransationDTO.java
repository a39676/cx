package demo.finance.account_info.pojo.dto.controllerDTO;

import java.math.BigDecimal;

public class InsertNewTransationDTO {

	private String accountNumber;
	private Integer transationType;
	private BigDecimal fixCreditQuota;
	private BigDecimal transationAmount;
	private String transationParties;
	private String transationDate;
	private String remark;


	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getTransationType() {
		return transationType;
	}

	public void setTransationType(Integer transationType) {
		this.transationType = transationType;
	}

	public String getTransationDate() {
		return transationDate;
	}

	public void setTransationDate(String transationDate) {
		this.transationDate = transationDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFixCreditQuota() {
		return fixCreditQuota;
	}

	public void setFixCreditQuota(BigDecimal fixCreditQuota) {
		this.fixCreditQuota = fixCreditQuota;
	}

	public BigDecimal getTransationAmount() {
		return transationAmount;
	}

	public void setTransationAmount(BigDecimal transationAmount) {
		this.transationAmount = transationAmount;
	}

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties;
	}

	@Override
	public String toString() {
		return "InsertNewTransationParam [accountNumber=" + accountNumber + ", transationType=" + transationType
				+ ", fixCreditQuota=" + fixCreditQuota + ", transationAmount=" + transationAmount
				+ ", transationParties=" + transationParties + ", transationDate=" + transationDate + ", remark="
				+ remark + "]";
	}

}
