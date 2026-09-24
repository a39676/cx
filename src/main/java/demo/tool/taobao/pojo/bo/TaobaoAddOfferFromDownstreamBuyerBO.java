package demo.tool.taobao.pojo.bo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaobaoAddOfferFromDownstreamBuyerBO {

	private Long idOutsource;
	private BigDecimal amount;
	private String addressInfo;
	private Integer afterTransitRegionId;
	private String buyerWangWangName;
	private String packageReceiverName;
	private String phone;
	private String remark;
	private LocalDateTime orderCreateTime;
	private LocalDateTime paymentTime;

	public Long getIdOutsource() {
		return idOutsource;
	}

	public void setIdOutsource(Long idOutsource) {
		this.idOutsource = idOutsource;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public Integer getAfterTransitRegionId() {
		return afterTransitRegionId;
	}

	public void setAfterTransitRegionId(Integer afterTransitRegionId) {
		this.afterTransitRegionId = afterTransitRegionId;
	}

	public String getBuyerWangWangName() {
		return buyerWangWangName;
	}

	public void setBuyerWangWangName(String buyerWangWangName) {
		this.buyerWangWangName = buyerWangWangName;
	}

	public String getPackageReceiverName() {
		return packageReceiverName;
	}

	public void setPackageReceiverName(String packageReceiverName) {
		this.packageReceiverName = packageReceiverName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(LocalDateTime orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(LocalDateTime paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Override
	public String toString() {
		return "TaobaoAddOfferFromDownstreamBuyerBO [idOutsource=" + idOutsource + ", amount=" + amount
				+ ", addressInfo=" + addressInfo + ", afterTransitRegionId=" + afterTransitRegionId
				+ ", buyerWangWangName=" + buyerWangWangName + ", packageReceiverName=" + packageReceiverName
				+ ", phone=" + phone + ", remark=" + remark + ", orderCreateTime=" + orderCreateTime + ", paymentTime="
				+ paymentTime + "]";
	}

}
