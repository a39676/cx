package demo.account_info.pojo.dto.controllerDTO;

public class GetAccountListByConditionParam {
	
	private Long bankId;
	private Long bankUnionId;
	private Integer accountType;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getBankUnionId() {
		return bankUnionId;
	}

	public void setBankUnionId(Long bankUnionId) {
		this.bankUnionId = bankUnionId;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "GetAccountListByConditionParam [bankId=" + bankId + ", bankUnionId=" + bankUnionId + ", accountType="
				+ accountType + "]";
	}

	
}
