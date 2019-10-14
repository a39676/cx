package demo.account_info.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.account_info.pojo.bo.AccountInfoWithBankInfo;

public class GetAccountListResult extends CommonResult {

	private List<AccountInfoWithBankInfo> accountList;

	public List<AccountInfoWithBankInfo> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountInfoWithBankInfo> accountList) {
		this.accountList = accountList;
	}

	@Override
	public String toString() {
		return "GetAccountListResult [accountList=" + accountList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
