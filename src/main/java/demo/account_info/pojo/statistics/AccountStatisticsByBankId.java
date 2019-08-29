package demo.account_info.pojo.statistics;

import java.math.BigDecimal;
import java.util.Date;

import demo.account_info.pojo.bo.AccountInfoWithBankInfo;

public class AccountStatisticsByBankId {

	private Long bankId;
	
	private BigDecimal totalLiability = BigDecimal.ZERO;
	
	private BigDecimal sharingQuota = BigDecimal.ZERO;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getTotalLiability() {
		return totalLiability;
	}

	public void setTotalLiability(BigDecimal totalLiability) {
		this.totalLiability = totalLiability;
	}

	public BigDecimal getSharingQuota() {
		return sharingQuota;
	}

	public void setSharingQuota(BigDecimal sharingQuota) {
		this.sharingQuota = sharingQuota;
	}
	
	public void addLiability(BigDecimal inputLiability) {
		if(inputLiability.compareTo(BigDecimal.ZERO) < 0) {
			this.totalLiability = this.totalLiability.add(inputLiability);
		}
	}
	
	/**
	 * 设定同一银行下最高授信额度
	 * 只统计有效期内的卡片
	 * @param account
	 */
	public void addSharingQuota(AccountInfoWithBankInfo account) {
		if(account.getVaildDate().after(new Date()) 
				&& this.sharingQuota.compareTo(account.getCreditsQuota().add(account.getTemproraryCreditsQuota())) < 0) {
			this.sharingQuota = account.getCreditsQuota().add(account.getTemproraryCreditsQuota());
		}
	}
	
	public BigDecimal getRemainingQuota() {
		return sharingQuota.add(totalLiability);
	}

	@Override
	public String toString() {
		return "AccountStatisticsByBankId [bankId=" + bankId + ", totalLiability=" + totalLiability + ", sharingQuota="
				+ sharingQuota + "]";
	}
	
	
}
