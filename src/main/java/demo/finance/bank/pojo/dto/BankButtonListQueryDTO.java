package demo.finance.bank.pojo.dto;

public class BankButtonListQueryDTO {

	private String bankName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "BankButtonListQueryDTO [bankName=" + bankName + "]";
	}
}
