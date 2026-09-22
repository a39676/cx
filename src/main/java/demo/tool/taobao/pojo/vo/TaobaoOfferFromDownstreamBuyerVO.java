package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;

public class TaobaoOfferFromDownstreamBuyerVO {

	private String orderID;
	private BigDecimal amount;
	private String phone;
	private String nickname;
	private String packageReceiverName;
	private String address;
	private String afterTransitRegionName;
	private Integer afterTransitRegionID;
	private String createTimeStr;
	private String remark;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPackageReceiverName() {
		return packageReceiverName;
	}

	public void setPackageReceiverName(String packageReceiverName) {
		this.packageReceiverName = packageReceiverName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAfterTransitRegionName() {
		return afterTransitRegionName;
	}

	public void setAfterTransitRegionName(String afterTransitRegionName) {
		this.afterTransitRegionName = afterTransitRegionName;
	}

	public Integer getAfterTransitRegionID() {
		return afterTransitRegionID;
	}

	public void setAfterTransitRegionID(Integer afterTransitRegionID) {
		this.afterTransitRegionID = afterTransitRegionID;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TaobaoOfferFromDownstreamBuyerVO [orderID=" + orderID + ", amount=" + amount + ", phone=" + phone
				+ ", nickname=" + nickname + ", packageReceiverName=" + packageReceiverName + ", address=" + address
				+ ", afterTransitRegionName=" + afterTransitRegionName + ", afterTransitRegionID="
				+ afterTransitRegionID + ", createTimeStr=" + createTimeStr + ", remark=" + remark + "]";
	}

}
