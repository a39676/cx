package demo.finance.bank.pojo.result;

import java.util.List;

import demo.baseCommon.pojo.result.CommonResultCX;
import demo.finance.bank.pojo.po.BankInfo;

public class FindBankInfoResult extends CommonResultCX {
	
	private List<BankInfo> bankList;

	public List<BankInfo> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankInfo> bankList) {
		this.bankList = bankList;
	}

	@Override
	public String toString() {
		return "FindBankInfoResult [bankList=" + bankList + "]";
	}


}
