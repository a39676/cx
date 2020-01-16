package demo.finance.account_info.pojo.dto.controllerDTO;

public class AccountInfoDetailQueryDTO {

	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "AccountInfoDetailQueryDTO [accountNumber=" + accountNumber + "]";
	}

}
