package demo.trading.pojo;

import java.util.Date;

public class CommonTransationParties {
    private Integer transationPartnerId;

    private String transationPartnerName;

    private Integer consumptionType;

    private Date createTime;

    private String remark;

    public Integer getTransationPartnerId() {
        return transationPartnerId;
    }

    public void setTransationPartnerId(Integer transationPartnerId) {
        this.transationPartnerId = transationPartnerId;
    }

    public String getTransationPartnerName() {
        return transationPartnerName;
    }

    public void setTransationPartnerName(String transationPartnerName) {
        this.transationPartnerName = transationPartnerName == null ? null : transationPartnerName.trim();
    }

    public Integer getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(Integer consumptionType) {
        this.consumptionType = consumptionType;
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
}