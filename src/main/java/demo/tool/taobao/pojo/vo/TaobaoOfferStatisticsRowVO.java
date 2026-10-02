package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

public class TaobaoOfferStatisticsRowVO {

	private TaobaoOfferFromDownstreamBuyerVO buyerOrderVO;
	private List<TaobaoRefundSubOrderVO> refundOrderList;
	private BigDecimal totalRefundAmount;
	private List<TaobaoOfferToSupplierVO> supplierOrderVoList;
	private BigDecimal totalSupplierOrderAmount;
	private BigDecimal profit;

	public TaobaoOfferFromDownstreamBuyerVO getBuyerOrderVO() {
		return buyerOrderVO;
	}

	public void setBuyerOrderVO(TaobaoOfferFromDownstreamBuyerVO buyerOrderVO) {
		this.buyerOrderVO = buyerOrderVO;
	}

	public List<TaobaoRefundSubOrderVO> getRefundOrderList() {
		return refundOrderList;
	}

	public void setRefundOrderList(List<TaobaoRefundSubOrderVO> refundOrderList) {
		this.refundOrderList = refundOrderList;
	}

	public BigDecimal getTotalRefundAmount() {
		return totalRefundAmount;
	}

	public void setTotalRefundAmount(BigDecimal totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}

	public List<TaobaoOfferToSupplierVO> getSupplierOrderVoList() {
		return supplierOrderVoList;
	}

	public void setSupplierOrderVoList(List<TaobaoOfferToSupplierVO> supplierOrderVoList) {
		this.supplierOrderVoList = supplierOrderVoList;
	}

	public BigDecimal getTotalSupplierOrderAmount() {
		return totalSupplierOrderAmount;
	}

	public void setTotalSupplierOrderAmount(BigDecimal totalSupplierOrderAmount) {
		this.totalSupplierOrderAmount = totalSupplierOrderAmount;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "TaobaoOfferStatisticsRowVO [buyerOrderVO=" + buyerOrderVO + ", refundOrderList=" + refundOrderList
				+ ", totalRefundAmount=" + totalRefundAmount + ", supplierOrderVoList=" + supplierOrderVoList
				+ ", totalSupplierOrderAmount=" + totalSupplierOrderAmount + ", profit=" + profit + "]";
	}

}
