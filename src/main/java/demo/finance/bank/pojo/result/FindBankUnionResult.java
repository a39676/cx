package demo.finance.bank.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.bank.pojo.po.BankUnion;

public class FindBankUnionResult extends CommonResult {

	private List<BankUnion> bankUnionList;

	public List<BankUnion> getBankUnionList() {
		return bankUnionList;
	}

	public void setBankUnionList(List<BankUnion> bankUnionList) {
		this.bankUnionList = bankUnionList;
	}

	@Override
	public String toString() {
		return "FindBankUnionResult [bankUnionList=" + bankUnionList + "]";
	}

}
