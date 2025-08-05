package demo.tool.wodian.pojo.dto;

public class WodianContractSerachConditionDTO {

	private Long salesmanId;
	private Long merchantsId;
	private String startDateStr;
	private String endDateStr;
	private Integer version;

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

	@Override
	public String toString() {
		return "WodianContractSerachConditionDTO [salesmanId=" + salesmanId + ", merchantsId=" + merchantsId
				+ ", startDateStr=" + startDateStr + ", endDateStr=" + endDateStr + ", version=" + version + "]";
	}

}
