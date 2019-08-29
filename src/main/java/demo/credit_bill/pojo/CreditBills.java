package demo.credit_bill.pojo;

import java.math.BigDecimal;
import java.util.Date;

import demo.util.EncryptUtil;

public class CreditBills {
    private Integer billId;

    private Integer accountId;

    private Date lastRefundDate;

    private BigDecimal billAmount;

    private BigDecimal minRefundAmount;

    private Date createTime;

    private String remark;

    private String marker;
    
    
	@Override
	public String toString() {
		return "CreditBills [billId=" + billId + ", accountId=" + accountId + ", lastRefundDate=" + lastRefundDate
				+ ", billAmount=" + billAmount + ", minRefundAmount=" + minRefundAmount + ", createTime=" + createTime
				+ ", remark=" + remark + ", marker=" + marker + "]";
	}

	public String getInfos() {
		return "" + billId + accountId + lastRefundDate + billAmount + minRefundAmount
				+ createTime + remark;
	}

	public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getLastRefundDate() {
        return lastRefundDate;
    }

    public void setLastRefundDate(Date lastRefundDate) {
        this.lastRefundDate = lastRefundDate;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getMinRefundAmount() {
        return minRefundAmount;
    }

    public void setMinRefundAmount(BigDecimal minRefundAmount) {
        this.minRefundAmount = minRefundAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker() {
        this.marker = EncryptUtil.Sha1(EncryptUtil.ToMd5String(getInfos()));
    }
}