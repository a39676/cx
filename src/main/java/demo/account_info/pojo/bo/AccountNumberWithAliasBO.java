package demo.account_info.pojo.bo;

public class AccountNumberWithAliasBO {

	private Long accountId;
	private String accountNumber;
	private String alias;

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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		return "AccountNumberWithAliasBO [accountId=" + accountId + ", accountNumber=" + accountNumber + ", alias="
				+ alias + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
