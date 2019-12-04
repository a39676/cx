package demo.finance.trading.pojo;

import java.util.Date;

public class HolderCommonTransation {
    private Integer transationPartnerId;

    private Integer accountHolderId;

    private Date createTime;

    private Date lastUseTime;

    private String remark;

    public Integer getTransationPartnerId() {
        return transationPartnerId;
    }

    public void setTransationPartnerId(Integer transationPartnerId) {
        this.transationPartnerId = transationPartnerId;
    }

    public Integer getAccountHolderId() {
        return accountHolderId;
    }

    public void setAccountHolderId(Integer accountHolderId) {
        this.accountHolderId = accountHolderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}