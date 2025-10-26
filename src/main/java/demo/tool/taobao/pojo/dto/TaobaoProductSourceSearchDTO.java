package demo.tool.taobao.pojo.dto;

public class TaobaoProductSourceSearchDTO {

	private String commodityIdListStr;
	private String sourceIdIdListStr;
	private String commodityName;
	private String commodityImgName;
	private Boolean includePostage = false;
	private String remark;

	public String getCommodityIdListStr() {
		return commodityIdListStr;
	}

	public void setCommodityIdListStr(String commodityIdListStr) {
		this.commodityIdListStr = commodityIdListStr;
	}

	public String getSourceIdIdListStr() {
		return sourceIdIdListStr;
	}

	public void setSourceIdIdListStr(String sourceIdIdListStr) {
		this.sourceIdIdListStr = sourceIdIdListStr;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityImgName() {
		return commodityImgName;
	}

	public void setCommodityImgName(String commodityImgName) {
		this.commodityImgName = commodityImgName;
	}

	public Boolean getIncludePostage() {
		return includePostage;
	}

	public void setIncludePostage(Boolean includePostage) {
		this.includePostage = includePostage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TaobaoProductSourceSearchDTO [commodityIdListStr=" + commodityIdListStr + ", sourceIdIdListStr="
				+ sourceIdIdListStr + ", commodityName=" + commodityName + ", commodityImgName=" + commodityImgName
				+ ", includePostage=" + includePostage + ", remark=" + remark + "]";
	}

}
