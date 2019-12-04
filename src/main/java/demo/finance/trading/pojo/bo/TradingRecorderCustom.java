package demo.finance.trading.pojo.bo;

import java.util.Date;

import demo.finance.account_holder.pojo.po.AccountHolder;
import demo.finance.trading.pojo.po.TradingRecorder;

public class TradingRecorderCustom extends TradingRecorder{
	
	private AccountHolder accountHolder;

	private Date startTime;
	
	private Date endTime;
	
	
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}
	
	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

}
