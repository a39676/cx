package demo.tool.wodian.pojo.dto;

import java.math.BigDecimal;

public class WodianSummaryContractDataBySalesmanDTO {

	private Long salesmanId;
	private String salesmanName;
	private BigDecimal summaryAmount;

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

	public BigDecimal getSummaryAmount() {
		return summaryAmount;
	}

	public void setSummaryAmount(BigDecimal summaryAmount) {
		this.summaryAmount = summaryAmount;
	}

	@Override
	public String toString() {
		return "WodianSummaryContractDataBySalesmanDTO [salesmanId=" + salesmanId + ", salesmanName=" + salesmanName
				+ ", summaryAmount=" + summaryAmount + "]";
	}

}
