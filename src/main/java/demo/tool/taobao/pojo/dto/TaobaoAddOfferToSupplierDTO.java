package demo.tool.taobao.pojo.dto;

import java.math.BigDecimal;

public class TaobaoAddOfferToSupplierDTO {

	private Long downstreamBuyerOrderId;
	private Long supplierOrderId;
	private BigDecimal amount;
	private String remark;
	private Long merchantID;

	public Long getDownstreamBuyerOrderId() {
		return downstreamBuyerOrderId;
	}

	public void setDownstreamBuyerOrderId(Long downstreamBuyerOrderId) {
		this.downstreamBuyerOrderId = downstreamBuyerOrderId;
	}

	public Long getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(Long supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
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

	public Long getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(Long merchantID) {
		this.merchantID = merchantID;
	}

	@Override
	public String toString() {
		return "TaobaoAddOfferToSupplierDTO [downstreamBuyerOrderId=" + downstreamBuyerOrderId + ", supplierOrderId="
				+ supplierOrderId + ", amount=" + amount + ", remark=" + remark + ", merchantID=" + merchantID + "]";
	}

}
