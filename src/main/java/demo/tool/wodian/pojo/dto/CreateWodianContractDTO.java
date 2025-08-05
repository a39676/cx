package demo.tool.wodian.pojo.dto;

import java.math.BigDecimal;

public class CreateWodianContractDTO {

	private String clientName;
	private String clientPhoneNumber;
	private Long merchantsId;
	private Long salesmanId;
	private String contractCreateTimeStr;
	private BigDecimal contractAmount;
	private BigDecimal goldCoinForClient;
	private BigDecimal goleCoinForMerchants;
	private BigDecimal integralForClient;
	private BigDecimal integralForMerchants;
	private Integer partCounts;
	private String remark;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientPhoneNumber() {
		return clientPhoneNumber;
	}

	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}

	public Long getMerchantsId() {
		return merchantsId;
	}

	public void setMerchantsId(Long merchantsId) {
		this.merchantsId = merchantsId;
	}

	public Long getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(Long salesmanId) {
		this.salesmanId = salesmanId;
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
		return "CreateWodianContractDTO [clientName=" + clientName + ", clientPhoneNumber=" + clientPhoneNumber
				+ ", merchantsId=" + merchantsId + ", salesmanId=" + salesmanId + ", contractCreateTimeStr="
				+ contractCreateTimeStr + ", contractAmount=" + contractAmount + ", goldCoinForClient="
				+ goldCoinForClient + ", goleCoinForMerchants=" + goleCoinForMerchants + ", integralForClient="
				+ integralForClient + ", integralForMerchants=" + integralForMerchants + ", partCounts=" + partCounts
				+ ", remark=" + remark + "]";
	}

}
