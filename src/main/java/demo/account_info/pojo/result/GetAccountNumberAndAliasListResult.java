package demo.account_info.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.account_info.pojo.bo.AccountNumberWithAliasBO;

public class GetAccountNumberAndAliasListResult extends CommonResult {

	private List<AccountNumberWithAliasBO> accountList;

	public List<AccountNumberWithAliasBO> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountNumberWithAliasBO> accountList) {
		this.accountList = accountList;
	}

	@Override
	public String toString() {
		return "GetAccountListResult [accountList=" + accountList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
