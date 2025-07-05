package demo.tool.wodian.pojo.dto;

public class WodianContractSerachConditionDTO {

	private String startDateStr;
	private String endDateStr;

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

	@Override
	public String toString() {
		return "WodianContractSerachConditionDTO [startDateStr=" + startDateStr + ", endDateStr=" + endDateStr + "]";
	}

}
