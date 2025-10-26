package demo.tool.taobao.pojo.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TaobaoProductSourceAddDTO {

	private BigInteger commodityId;
	private BigDecimal sourceId;
	private String commodityName;
	private String commodityImgName;
	private Boolean includePostage = false;
	private String remark;

	public BigInteger getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(BigInteger commodityId) {
		this.commodityId = commodityId;
	}

	public BigDecimal getSourceId() {
		return sourceId;
	}

	public void setSourceId(BigDecimal sourceId) {
		this.sourceId = sourceId;
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
		return "AddTaobaoProductSourceDTO [commodityId=" + commodityId + ", sourceId=" + sourceId + ", commodityName="
				+ commodityName + ", commodityImgName=" + commodityImgName + ", includePostage=" + includePostage
				+ ", remark=" + remark + "]";
	}

}
