package demo.finance.account_info.pojo.dto.controllerDTO;

import java.math.BigDecimal;

public class ModifyCreditsQuotaDTO {

	private String accountNumber;
	private BigDecimal newCreditsQuota;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getNewCreditsQuota() {
		return newCreditsQuota;
	}

	public void setNewCreditsQuota(BigDecimal newCreditsQuota) {
		this.newCreditsQuota = newCreditsQuota;
	}

	@Override
	public String toString() {
		return "ModifyCreditsQuotaDTO [accountNumber=" + accountNumber + ", newCreditsQuota=" + newCreditsQuota + "]";
	}

}
