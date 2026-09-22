package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;

public class TaobaoOfferToSupplierVO {

	private String orderID;
	private BigDecimal amount;
	private String remark;
	private String supplierName;
	private String supplierID;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	@Override
	public String toString() {
		return "TaobaoOfferToSupplierVO [orderID=" + orderID + ", amount=" + amount + ", remark=" + remark
				+ ", supplierName=" + supplierName + ", supplierID=" + supplierID + "]";
	}

}
