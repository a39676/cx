package demo.tool.taobao.pojo.dto;

import java.math.BigDecimal;

public class TaobaoRefundOrderFromDownstreamBuyerDTO {

	private Long refundOrderId;
	private Long sourceOrderId;
	private BigDecimal amount;
	private String refundDateTimeStr;
	private String remark;

	public Long getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(Long refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public Long getSourceOrderId() {
		return sourceOrderId;
	}

	public void setSourceOrderId(Long sourceOrderId) {
		this.sourceOrderId = sourceOrderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRefundDateTimeStr() {
		return refundDateTimeStr;
	}

	public void setRefundDateTimeStr(String refundDateTimeStr) {
		this.refundDateTimeStr = refundDateTimeStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TaobaoRefundOrderFromDownstreamBuyerDTO [refundOrderId=" + refundOrderId + ", sourceOrderId="
				+ sourceOrderId + ", amount=" + amount + ", refundDateTimeStr=" + refundDateTimeStr + ", remark="
				+ remark + "]";
	}

}
