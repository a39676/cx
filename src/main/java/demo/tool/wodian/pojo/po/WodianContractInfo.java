package demo.tool.wodian.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WodianContractInfo {
    private Long id;

    private Long clientId;

    private Long salesmanId;

    private Long merchantsId;

    private LocalDateTime contractCreateTime;

    private BigDecimal contractAmount;

    private BigDecimal goldcoinForClient;

    private BigDecimal goldcoinForMerchants;

    private BigDecimal integralForClient;

    private BigDecimal integralForMerchants;

    private Integer partCounts;

    private BigDecimal version;

    private String remark;

    private LocalDateTime createTime;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
    }

    public Long getMerchantsId() {
        return merchantsId;
    }

    public void setMerchantsId(Long merchantsId) {
        this.merchantsId = merchantsId;
    }

    public LocalDateTime getContractCreateTime() {
        return contractCreateTime;
    }

    public void setContractCreateTime(LocalDateTime contractCreateTime) {
        this.contractCreateTime = contractCreateTime;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getGoldcoinForClient() {
        return goldcoinForClient;
    }

    public void setGoldcoinForClient(BigDecimal goldcoinForClient) {
        this.goldcoinForClient = goldcoinForClient;
    }

    public BigDecimal getGoldcoinForMerchants() {
        return goldcoinForMerchants;
    }

    public void setGoldcoinForMerchants(BigDecimal goldcoinForMerchants) {
        this.goldcoinForMerchants = goldcoinForMerchants;
    }

    public BigDecimal getIntegralForClient() {
        return integralForClient;
    }

    public void setIntegralForClient(BigDecimal integralForClient) {
        this.integralForClient = integralForClient;
    }

    public BigDecimal getIntegralForMerchants() {
        return integralForMerchants;
    }

    public void setIntegralForMerchants(BigDecimal integralForMerchants) {
        this.integralForMerchants = integralForMerchants;
    }

    public Integer getPartCounts() {
        return partCounts;
    }

    public void setPartCounts(Integer partCounts) {
        this.partCounts = partCounts;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
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