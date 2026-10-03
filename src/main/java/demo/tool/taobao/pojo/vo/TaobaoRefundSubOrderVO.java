package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TaobaoRefundSubOrderVO {

	private Long orderIdFromDownstreamBuyer;
	private Long refundOrderId;
	private BigDecimal amount;
	private LocalDateTime refundOrderDateTime;
	private String refundOrderDateTimeStr;
	private String remark;

	public Long getOrderIdFromDownstreamBuyer() {
		return orderIdFromDownstreamBuyer;
	}

	public void setOrderIdFromDownstreamBuyer(Long orderIdFromDownstreamBuyer) {
		this.orderIdFromDownstreamBuyer = orderIdFromDownstreamBuyer;
	}

	public Long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getRefundOrderDateTime() {
		return refundOrderDateTime;
	}

	public void setRefundOrderDateTime(LocalDateTime refundOrderDateTime) {
		this.refundOrderDateTime = refundOrderDateTime;
	}

	public String getRefundOrderDateTimeStr() {
		return refundOrderDateTimeStr;
	}

	public void setRefundOrderDateTimeStr(String refundOrderDateTimeStr) {
		this.refundOrderDateTimeStr = refundOrderDateTimeStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TaobaoRefundSubOrderVO [orderIdFromDownstreamBuyer=" + orderIdFromDownstreamBuyer + ", refundOrderId="
				+ refundOrderId + ", amount=" + amount + ", refundOrderDateTime=" + refundOrderDateTime
				+ ", refundOrderDateTimeStr=" + refundOrderDateTimeStr + ", remark=" + remark + "]";
	}

}
