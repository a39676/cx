package demo.tool.taobao.pojo.dto;

public class TaobaoOfferStatisticsQueryDTO {
	
	private String buyerOrderId;
	private String startTimeStr;
	private String endTimeStr;

	public String getBuyerOrderId() {
		return buyerOrderId;
	}

	public void setBuyerOrderId(String buyerOrderId) {
		this.buyerOrderId = buyerOrderId;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	@Override
	public String toString() {
		return "TaobaoOfferStatisticsQueryDTO [buyerOrderId=" + buyerOrderId + ", startTimeStr=" + startTimeStr
				+ ", endTimeStr=" + endTimeStr + "]";
	}

}
