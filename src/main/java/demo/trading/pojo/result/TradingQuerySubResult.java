package demo.trading.pojo.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TradingQuerySubResult {

	private LocalDateTime tradingDate;
	private BigDecimal amount;
	private String bankName;
	private String accountAlias;
	private String accountNumber;
	private String transationParties;

	public LocalDateTime getTradingDate() {
		return tradingDate;
	}

	public void setTradingDate(LocalDateTime tradingDate) {
		this.tradingDate = tradingDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountAlias() {
		return accountAlias;
	}

	public void setAccountAlias(String accountAlias) {
		this.accountAlias = accountAlias;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransationParties() {
		return transationParties;
	}

	public void setTransationParties(String transationParties) {
		this.transationParties = transationParties;
	}

	@Override
	public String toString() {
		return "TradingQuerySubResult [tradingDate=" + tradingDate + ", amount=" + amount + ", bankName=" + bankName
				+ ", accountAlias=" + accountAlias + ", accountNumber=" + accountNumber + ", transationParties="
				+ transationParties + "]";
	}

}
