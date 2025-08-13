package demo.tool.wodian.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WodianContractInfoVO implements Comparable<WodianContractInfoVO> {

	private Long id;
	private Long clientId;
	private String clientName;
	private Long salesmanId;
	private String salesmanName;
	private Long merchantId;
	private String merchantsName;
	private LocalDateTime contractCreateTime;
	private String contractCreateTimeStr;
	private BigDecimal contractAmount;
	private BigDecimal goldCoinForClient;
	private BigDecimal goleCoinForMerchants;
	private BigDecimal integralForClient;
	private BigDecimal integralForMerchants;
	private Integer partCounts;
	private String remark;

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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(Long salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantsName() {
		return merchantsName;
	}

	public void setMerchantsName(String merchantsName) {
		this.merchantsName = merchantsName;
	}

	public LocalDateTime getContractCreateTime() {
		return contractCreateTime;
	}

	public void setContractCreateTime(LocalDateTime contractCreateTime) {
		this.contractCreateTime = contractCreateTime;
	}

	public String getContractCreateTimeStr() {
		return contractCreateTimeStr;
	}

	public void setContractCreateTimeStr(String contractCreateTimeStr) {
		this.contractCreateTimeStr = contractCreateTimeStr;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public BigDecimal getGoldCoinForClient() {
		return goldCoinForClient;
	}

	public void setGoldCoinForClient(BigDecimal goldCoinForClient) {
		this.goldCoinForClient = goldCoinForClient;
	}

	public BigDecimal getGoleCoinForMerchants() {
		return goleCoinForMerchants;
	}

	public void setGoleCoinForMerchants(BigDecimal goleCoinForMerchants) {
		this.goleCoinForMerchants = goleCoinForMerchants;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "WodianContractInfoVO [id=" + id + ", clientId=" + clientId + ", clientName=" + clientName
				+ ", salesmanId=" + salesmanId + ", salesmanName=" + salesmanName + ", merchantId=" + merchantId
				+ ", merchantsName=" + merchantsName + ", contractCreateTime=" + contractCreateTime
				+ ", contractCreateTimeStr=" + contractCreateTimeStr + ", contractAmount=" + contractAmount
				+ ", goldCoinForClient=" + goldCoinForClient + ", goleCoinForMerchants=" + goleCoinForMerchants
				+ ", integralForClient=" + integralForClient + ", integralForMerchants=" + integralForMerchants
				+ ", partCounts=" + partCounts + ", remark=" + remark + "]";
	}

	@Override
	public int compareTo(WodianContractInfoVO o) {
		return compareWithContractCreateTime(o);
	}

	private int compareWithContractCreateTime(WodianContractInfoVO o) {
		if (this.contractCreateTime.isBefore(o.contractCreateTime)) {
			return -1;
		} else if (this.contractCreateTime.isEqual(o.contractCreateTime)) {
			return 0;
		}
		return 1;
	}
}
