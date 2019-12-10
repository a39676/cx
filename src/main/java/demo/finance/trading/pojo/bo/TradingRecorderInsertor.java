package demo.finance.trading.pojo.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import demo.finance.account_info.pojo.po.AccountInfo;
import demo.finance.trading.pojo.po.TradingRecorder;
import toolPack.numericHandel.NumericUtilCustom;

public class TradingRecorderInsertor extends TradingRecorder{

	private HashMap<String, String> accountNumbers;
	
	private String accountNumber;
	
	private String transationAmount;
	
	
	
	
	public String getTransationAmount() {
		return transationAmount;
	}

	public void setTransationAmount(String transationAmount) {
		NumericUtilCustom n = new NumericUtilCustom();
		if(StringUtils.isNotEmpty(transationAmount) && n.matchSimpleNumber(transationAmount)){
			this.transationAmount = transationAmount;
		} else {
			throw new NumberFormatException();
		}
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public HashMap<String, String> getAccountNumbers() {
		return accountNumbers;
	}

	public void setAccountNumbers(HashMap<String, String> accountNumbers) {
		this.accountNumbers = accountNumbers;
	}

	public void setAccountNumbers(List<AccountInfo> accountInfos) {
		accountNumbers = new HashMap<String, String>();
		if(accountInfos != null && accountInfos.size() > 0 ){
			for(AccountInfo info : accountInfos) {
				accountNumbers.put(info.getAccountNumber(), info.getAccountNumber());
			}
		} 
	}
	
	
}
