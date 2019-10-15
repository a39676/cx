package demo.bank.pojo.result;

import java.util.List;

import demo.bank.pojo.po.BankUnion;
import demo.baseCommon.pojo.result.CommonResultCX;

public class FindBankUnionResult extends CommonResultCX {

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
