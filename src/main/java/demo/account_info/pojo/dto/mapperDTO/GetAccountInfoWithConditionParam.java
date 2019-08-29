package demo.account_info.pojo.dto.mapperDTO;

import demo.account_info.pojo.type.AccountType;

public class GetAccountInfoWithConditionParam {

	private Long holderId;
	private String accountNumber;
	private Long bankId;
	private Long bankUnionId;
	private Boolean includeDeleted = false;
	/** {@link AccountType} */
	private Integer accountType;

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Long getHolderId() {
		return holderId;
	}

	public void setHolderId(Long holderId) {
		this.holderId = holderId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Boolean getIncludeDeleted() {
		return includeDeleted;
	}

	public void setIncludeDeleted(Boolean includeDeleted) {
		this.includeDeleted = includeDeleted;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	@Override
	public String toString() {
		return "GetAccountInfoWithConditionParam [holderId=" + holderId + ", accountNumber=" + accountNumber
				+ ", bankId=" + bankId + ", bankUnionId=" + bankUnionId + ", includeDeleted=" + includeDeleted
				+ ", accountType=" + accountType + "]";
	}

}
