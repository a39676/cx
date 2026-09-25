package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaobaoOfferFromDownstreamBuyerVO {

	private String orderID;
	private BigDecimal amount;
	private String phone;
	private String nickname;
	private String packageReceiverName;
	private String address;
	private String afterTransitRegionName;
	private Integer afterTransitRegionID;
	private LocalDateTime orderCreateTime;
	private String orderCreateTimeStr;
	private LocalDateTime orderPaymentTime;
	private String orderPaymentTimeStr;
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

	public LocalDateTime getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(LocalDateTime orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderCreateTimeStr() {
		return orderCreateTimeStr;
	}

	public void setOrderCreateTimeStr(String orderCreateTimeStr) {
		this.orderCreateTimeStr = orderCreateTimeStr;
	}

	public LocalDateTime getOrderPaymentTime() {
		return orderPaymentTime;
	}

	public void setOrderPaymentTime(LocalDateTime orderPaymentTime) {
		this.orderPaymentTime = orderPaymentTime;
	}

	public String getOrderPaymentTimeStr() {
		return orderPaymentTimeStr;
	}

	public void setOrderPaymentTimeStr(String orderPaymentTimeStr) {
		this.orderPaymentTimeStr = orderPaymentTimeStr;
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
				+ afterTransitRegionID + ", orderCreateTime=" + orderCreateTime + ", orderCreateTimeStr="
				+ orderCreateTimeStr + ", orderPaymentTime=" + orderPaymentTime + ", orderPaymentTimeStr="
				+ orderPaymentTimeStr + ", remark=" + remark + "]";
	}

}
