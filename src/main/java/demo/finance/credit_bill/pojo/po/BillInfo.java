package demo.finance.credit_bill.pojo.po;

import java.util.Date;

public class BillInfo {

	private Integer billInfoId;

	private Integer accountId;

	private Integer billDate;

	private Integer freeDays;

	private Integer lastRefundDate;

	private Boolean isDelete;

	private Date createTime;

	public Integer getBillInfoId() {
		return billInfoId;
	}

	public void setBillInfoId(Integer billInfoId) {
		this.billInfoId = billInfoId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getBillDate() {
		return billDate;
	}

	public void setBillDate(Integer billDate) {
		this.billDate = billDate;
	}

	public Integer getFreeDays() {
		return freeDays;
	}

	public void setFreeDays(Integer freeDays) {
		this.freeDays = freeDays;
	}

	public Integer getLastRefundDate() {
		return lastRefundDate;
	}

	public void setLastRefundDate(Integer lastRefundDate) {
		this.lastRefundDate = lastRefundDate;
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

	@Override
	public String toString() {
		return "BillInfo [billInfoId=" + billInfoId + ", accountId=" + accountId + ", billDate=" + billDate
				+ ", freeDays=" + freeDays + ", lastRefundDate=" + lastRefundDate + ", isDelete=" + isDelete
				+ ", createTime=" + createTime + "]";
	}

}