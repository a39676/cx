package demo.account_info.pojo.vo;

import java.math.BigDecimal;

public class SummaryAccountsByBankId {
	
	private Long bankId;
	
	private String bankChineseName;
	
	private BigDecimal sumCreditQuota = new BigDecimal(0);
	
	private BigDecimal maxCreditQuota = new BigDecimal(0);
	
	private BigDecimal sumSpentCreditQuota = new BigDecimal(0);
	
	private BigDecimal sumOverDeposit = new BigDecimal(0); 

	private BigDecimal sumDeposit = new BigDecimal(0);
	
	private BigDecimal subSum = new BigDecimal(0);
	
	

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	
	
	public String getBankChineseName() {
		return bankChineseName;
	}

	public void setBankChineseName(String bankChineseName) {
		this.bankChineseName = bankChineseName;
	}

	public BigDecimal getSumCreditQuota() {
		return sumCreditQuota;
	}

	public void setSumCreditQuota(BigDecimal sumCreditQuota) {
		this.sumCreditQuota = sumCreditQuota;
	}

	public BigDecimal getMaxCreditQuota() {
		return maxCreditQuota;
	}

	public void setMaxCreditQuota(BigDecimal maxCreditQuota) {
		this.maxCreditQuota = maxCreditQuota;
	}

	public BigDecimal getSumSpentCreditQuota() {
		return sumSpentCreditQuota;
	}

	public void setSumSpentCreditQuota(BigDecimal sumSpentCreditQuota) {
		this.sumSpentCreditQuota = sumSpentCreditQuota;
	}

	public BigDecimal getSumOverDeposit() {
		return sumOverDeposit;
	}

	public void setSumOverDeposit(BigDecimal sumOverDeposit) {
		this.sumOverDeposit = sumOverDeposit;
	}

	public BigDecimal getSumDeposit() {
		return sumDeposit;
	}

	public void setSumDeposit(BigDecimal sumDeposit) {
		this.sumDeposit = sumDeposit;
	}
	
	public BigDecimal getSubSum() {
		return subSum;
	}

	public void setSubSum(BigDecimal subSum) {
		this.subSum = subSum;
	}
	
	public void addCreditQuota(BigDecimal creditQuota) {
		this.sumCreditQuota = this.sumCreditQuota.add(creditQuota);
	}

	public void addMaxCreditQuota(BigDecimal creditQuota) {
		if(creditQuota != null && creditQuota.compareTo(this.maxCreditQuota) > 0) {
			this.maxCreditQuota = creditQuota;
		}
	}
	
	public void addSpentCredit(BigDecimal creditQuota) {
		this.sumSpentCreditQuota = this.sumSpentCreditQuota.add(creditQuota);
	}
	
	public void addOverDeposit(BigDecimal amount) {
		this.sumOverDeposit = this.sumOverDeposit.add(amount);
	}
	
	public void addDeposit(BigDecimal amount) {
		this.sumDeposit = this.sumDeposit.add(amount);
	}
	
	public void subSum() {
		this.subSum = this.sumDeposit.add(this.sumOverDeposit.add(this.sumSpentCreditQuota));
	}
}