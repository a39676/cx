package demo.finance.account_info.pojo.dto;

public class ModifyValidDateDTO {

	private String accountNumber;
	private String newVaildDate;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getNewVaildDate() {
		return newVaildDate;
	}

	public void setNewVaildDate(String newVaildDate) {
		this.newVaildDate = newVaildDate;
	}

	@Override
	public String toString() {
		return "ModifyValidDateDTO [accountNumber=" + accountNumber + ", newVaildDate=" + newVaildDate + "]";
	}

}
