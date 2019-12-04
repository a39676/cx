package demo.finance.bank.pojo.bo;

import java.util.List;

import demo.finance.bank.pojo.po.BankInfo;
import demo.finance.bank.pojo.po.BankUnion;

public class BankInfoCustomBO extends BankInfo{
	
	private BankUnion bankUnion;
	
	private List<BankInfo> bankInfoList;
	
	private List<BankUnion> bankUnionList;
	
	



	public List<BankUnion> getBankUnionList() {
		return bankUnionList;
	}

	public void setBankUnionList(List<BankUnion> bankUnionList) {
		this.bankUnionList = bankUnionList;
	}

	public List<BankInfo> getBankInfoList() {
		return bankInfoList;
	}

	public void setBankInfoList(List<BankInfo> bankInfoList) {
		this.bankInfoList = bankInfoList;
	}

	public BankUnion getBankUnion() {
		return bankUnion;
	}

	public void setBankUnion(BankUnion bankUnion) {
		this.bankUnion = bankUnion;
	}
	
}
