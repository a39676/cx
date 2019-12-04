package demo.finance.account_info.pojo.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountInfo implements Serializable{
	
	private static final long serialVersionUID = 2995906759514853605L;

	private Long accountId;

    private Long accountHolderId;

    private String accountNumber;
    
    private String accountAlias;

    private Integer accountType;

    private Long accountAffiliation;

    private Date vaildDate;

    private Long bankId;

    private Long bankUnionId;

    private BigDecimal accountBalance;

    private BigDecimal creditsQuota;

    private BigDecimal temproraryCreditsQuota;
    
    private Date temproraryCreditsVaildDate;

    private String remark;

    private Boolean isDelete;

    private Date createTime;
    
    

    
    public String getInfos() {
		return "" + accountId + accountHolderId 
				+ accountNumber + accountAlias + accountType + accountAffiliation
				+ vaildDate + bankId + bankUnionId
				+ accountBalance + creditsQuota + temproraryCreditsQuota + temproraryCreditsVaildDate 
				+ remark + isDelete + createTime;
	}

	public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Long accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }
    

    public String getAccountAlias() {
		return accountAlias;
	}

	public void setAccountAlias(String accountAlias) {
		this.accountAlias = accountAlias;
	}


    public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Long getAccountAffiliation() {
        return accountAffiliation;
    }

    public void setAccountAffiliation(Long accountAffiliation) {
        this.accountAffiliation = accountAffiliation;
    }

    public Date getVaildDate() {
        return vaildDate;
    }

    public void setVaildDate(Date vaildDate) {
        this.vaildDate = vaildDate;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getBankUnionId() {
        return bankUnionId;
    }

    public void setBankUnionId(Long bankUnionId) {
        this.bankUnionId = bankUnionId;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getCreditsQuota() {
        return creditsQuota;
    }

    public void setCreditsQuota(BigDecimal creditsQuota) {
        this.creditsQuota = creditsQuota;
    }

    public BigDecimal getTemproraryCreditsQuota() {
        return temproraryCreditsQuota;
    }

    public void setTemproraryCreditsQuota(BigDecimal temproraryCreditsQuota) {
        this.temproraryCreditsQuota = temproraryCreditsQuota;
    }

    public Date getTemproraryCreditsVaildDate() {
        return temproraryCreditsVaildDate;
    }

    public void setTemproraryCreditsVaildDate(Date temproraryCreditsVaildDate) {
        this.temproraryCreditsVaildDate = temproraryCreditsVaildDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public BigDecimal getTotalCreditQuota() {
    	return this.creditsQuota.add(this.temproraryCreditsQuota);
    }

	@Override
	public String toString() {
		return "AccountInfo [accountId=" + accountId + ", accountHolderId=" + accountHolderId + ", accountNumber="
				+ accountNumber + ", accountAlias=" + accountAlias + ", accountType=" + accountType
				+ ", accountAffiliation=" + accountAffiliation + ", vaildDate=" + vaildDate + ", bankId=" + bankId
				+ ", bankUnionId=" + bankUnionId + ", accountBalance=" + accountBalance + ", creditsQuota="
				+ creditsQuota + ", temproraryCreditsQuota=" + temproraryCreditsQuota + ", temproraryCreditsVaildDate="
				+ temproraryCreditsVaildDate + ", remark=" + remark + ", isDelete=" + isDelete + ", createTime="
				+ createTime + "]";
	}
    
    
}