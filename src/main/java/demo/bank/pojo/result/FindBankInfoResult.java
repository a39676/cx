package demo.bank.pojo.result;

import java.util.List;

import demo.bank.pojo.po.BankInfo;
import demo.baseCommon.pojo.result.CommonResult;

public class FindBankInfoResult extends CommonResult {
	
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
