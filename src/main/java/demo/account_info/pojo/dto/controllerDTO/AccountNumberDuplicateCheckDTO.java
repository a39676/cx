package demo.account_info.pojo.dto.controllerDTO;

public class AccountNumberDuplicateCheckDTO {

	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "AccountNumberDuplicateCheckDTO [accountNumber=" + accountNumber + "]";
	}

}
