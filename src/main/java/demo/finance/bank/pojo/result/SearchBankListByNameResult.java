package demo.finance.bank.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.bank.pojo.po.BankInfo;

public class SearchBankListByNameResult extends CommonResult {

	private List<BankInfo> bankList;

	public List<BankInfo> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankInfo> bankList) {
		this.bankList = bankList;
	}

	@Override
	public String toString() {
		return "SearchBankListByNameResult [bankList=" + bankList + "]";
	}

}
