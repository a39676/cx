package demo.finance.cryptoCoin.mining.pojo.dto;

public class CryptoCoinSharingCalculateDetailSearchDTO {

	private String pk;

	private String startDateStr;

	private String endDateStr;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
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

	@Override
	public String toString() {
		return "CryptoCoinSharingCalculateDetailSearchDTO [pk=" + pk + ", startDateStr=" + startDateStr
				+ ", endDateStr=" + endDateStr + "]";
	}

}
