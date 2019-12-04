package demo.finance.account_info.pojo.bo;

import java.math.BigDecimal;

import demo.finance.account_info.pojo.po.AccountInfo;

public class AccountInfoWithBankInfo extends AccountInfo {
	/*
	 * FIXME
	 * 2019-07-04
	 * 不应该直接继承po类  需要重新审视实际用途
	 */
	
	private static final long serialVersionUID = 8373791032244598124L;
	
	private Long bankId;

	private String bankEname;

	private String bankEnameShort;

	private String bankChineseName;

	private String bankChineseNameShort;

	private Long bankUnionId;

	private Boolean commonBank;

	private BigDecimal avaliableQuota;


	@Override
	public String toString() {
		return "AccountInfoWithBankInfo [bankId=" + bankId + ", bankEname=" + bankEname + ", bankEnameShort="
				+ bankEnameShort + ", bankChineseName=" + bankChineseName + ", bankChineseNameShort="
				+ bankChineseNameShort + ", bankUnionId=" + bankUnionId + ", commonBank=" + commonBank
				+ ", avaliableQuota=" + avaliableQuota + ", toString()=" + super.toString() + ", getInfos()="
				+ getInfos() + ", getAccountId()=" + getAccountId() + ", getAccountHolderId()=" + getAccountHolderId()
				+ ", getAccountNumber()=" + getAccountNumber() + ", getAccountAlias()=" + getAccountAlias()
				+ ", getAccountType()=" + getAccountType() + ", getAccountAffiliation()=" + getAccountAffiliation()
				+ ", getVaildDate()=" + getVaildDate() + ", getAccountBalance()=" + getAccountBalance()
				+ ", getCreditsQuota()=" + getCreditsQuota() + ", getTemproraryCreditsQuota()="
				+ getTemproraryCreditsQuota() + ", getTemproraryCreditsVaildDate()=" + getTemproraryCreditsVaildDate()
				+ ", getRemark()=" + getRemark() + ", getIsDelete()=" + getIsDelete() + ", getCreateTime()="
				+ getCreateTime() + ", getTotalCreditQuota()=" + getTotalCreditQuota() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankEname() {
		return bankEname;
	}

	public void setBankEname(String bankEname) {
		this.bankEname = bankEname;
	}

	public String getBankEnameShort() {
		return bankEnameShort;
	}

	public void setBankEnameShort(String bankEnameShort) {
		this.bankEnameShort = bankEnameShort;
	}

	public String getBankChineseName() {
		return bankChineseName;
	}

	public void setBankChineseName(String bankChineseName) {
		this.bankChineseName = bankChineseName;
	}

	public String getBankChineseNameShort() {
		return bankChineseNameShort;
	}

	public void setBankChineseNameShort(String bankChineseNameShort) {
		this.bankChineseNameShort = bankChineseNameShort;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public Boolean getCommonBank() {
		return commonBank;
	}

	public void setCommonBank(Boolean commonBank) {
		this.commonBank = commonBank;
	}

	public BigDecimal getAvaliableQuota() {
		return avaliableQuota;
	}

	public void setAvaliableQuota(BigDecimal avaliableQuota) {
		this.avaliableQuota = avaliableQuota;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
