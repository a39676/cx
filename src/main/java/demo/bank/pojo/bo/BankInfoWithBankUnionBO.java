package demo.bank.pojo.bo;

import demo.bank.pojo.po.BankInfo;
import demo.bank.pojo.po.BankUnion;

public class BankInfoWithBankUnionBO extends BankInfo{
	
	private BankUnion bankUnion;

	public BankUnion getBankUnion() {
		return bankUnion;
	}

	public void setBankUnion(BankUnion bankUnion) {
		this.bankUnion = bankUnion;
	}
	
	

}
