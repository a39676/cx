package demo.account_info.pojo.dto.controllerDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import demo.account_info.pojo.type.AccountType;

public class AccountInfoRegistDTO {

	private String accountNumber;
	private String accountAlias;
	/** {@link AccountType} */
	private Integer accountType;
	private Long bankUnionId;
	private Long bankId;
	private Long accountAffiliation;
	private LocalDate vaildDate;
	private BigDecimal accountBalance;
	private BigDecimal creditsQuota;
	private BigDecimal temproraryCreditsQuota;
	private LocalDate temproraryCreditsVaildDate;
	private String remark;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountAlias() {
		return accountAlias;
	}

	public void setAccountAlias(String accountAlias) {
		this.accountAlias = accountAlias;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getAccountAffiliation() {
		return accountAffiliation;
	}

	public void setAccountAffiliation(Long accountAffiliation) {
		this.accountAffiliation = accountAffiliation;
	}

	public LocalDate getVaildDate() {
		return vaildDate;
	}

	public void setVaildDate(LocalDate vaildDate) {
		this.vaildDate = vaildDate;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getCreditsQuota() {
		return creditsQuota;
	}

	public void setCreditsQuota(BigDecimal creditsQuota) {
		this.creditsQuota = creditsQuota;
	}

	public BigDecimal getTemproraryCreditsQuota() {
		return temproraryCreditsQuota;
	}

	public void setTemproraryCreditsQuota(BigDecimal temproraryCreditsQuota) {
		this.temproraryCreditsQuota = temproraryCreditsQuota;
	}

	public LocalDate getTemproraryCreditsVaildDate() {
		return temproraryCreditsVaildDate;
	}

	public void setTemproraryCreditsVaildDate(LocalDate temproraryCreditsVaildDate) {
		this.temproraryCreditsVaildDate = temproraryCreditsVaildDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AccountInfoRegistDTO [accountNumber=" + accountNumber + ", accountAlias=" + accountAlias
				+ ", accountType=" + accountType + ", bankUnionId=" + bankUnionId + ", bankId=" + bankId
				+ ", accountAffiliation=" + accountAffiliation + ", vaildDate=" + vaildDate + ", accountBalance="
				+ accountBalance + ", creditsQuota=" + creditsQuota + ", temproraryCreditsQuota="
				+ temproraryCreditsQuota + ", temproraryCreditsVaildDate=" + temproraryCreditsVaildDate + ", remark="
				+ remark + "]";
	}

}
