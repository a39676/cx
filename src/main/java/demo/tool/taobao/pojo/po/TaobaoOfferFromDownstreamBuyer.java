package demo.tool.taobao.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaobaoOfferFromDownstreamBuyer {
    private Long id;

    private Long idOutsource;

    private BigDecimal amount;

    private String address;

    private String phone;

    private String nickname;

    private String packageReceiverName;

    private Integer regionId1;

    private Integer regionId2;

    private Integer afterTransitRegionId;

    private String remark;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOutsource() {
        return idOutsource;
    }

    public void setIdOutsource(Long idOutsource) {
        this.idOutsource = idOutsource;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPackageReceiverName() {
        return packageReceiverName;
    }

    public void setPackageReceiverName(String packageReceiverName) {
        this.packageReceiverName = packageReceiverName == null ? null : packageReceiverName.trim();
    }

    public Integer getRegionId1() {
        return regionId1;
    }

    public void setRegionId1(Integer regionId1) {
        this.regionId1 = regionId1;
    }

    public Integer getRegionId2() {
        return regionId2;
    }

    public void setRegionId2(Integer regionId2) {
        this.regionId2 = regionId2;
    }

    public Integer getAfterTransitRegionId() {
        return afterTransitRegionId;
    }

    public void setAfterTransitRegionId(Integer afterTransitRegionId) {
        this.afterTransitRegionId = afterTransitRegionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}