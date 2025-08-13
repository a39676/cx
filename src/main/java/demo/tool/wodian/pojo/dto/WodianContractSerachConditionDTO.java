package demo.tool.wodian.pojo.dto;

public class WodianContractSerachConditionDTO {

	private Long salesmanId;
	private Long merchantsId;
	private String startDateStr;
	private String endDateStr;
	private Integer version;
	private String clientName;
	private String clientPhoneNumber;

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

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

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

	@Override
	public String toString() {
		return "WodianContractSerachConditionDTO [salesmanId=" + salesmanId + ", merchantsId=" + merchantsId
				+ ", startDateStr=" + startDateStr + ", endDateStr=" + endDateStr + ", version=" + version
				+ ", clientName=" + clientName + ", clientPhoneNumber=" + clientPhoneNumber + "]";
	}

}
