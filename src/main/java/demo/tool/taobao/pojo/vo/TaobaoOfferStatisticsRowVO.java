package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

public class TaobaoOfferStatisticsRowVO {

	private TaobaoOfferFromDownstreamBuyerVO buyerOrderVO;
	private List<TaobaoOfferToSupplierVO> supplierOrderVoList;
	private BigDecimal profit;

	public TaobaoOfferFromDownstreamBuyerVO getBuyerOrderVO() {
		return buyerOrderVO;
	}

	public void setBuyerOrderVO(TaobaoOfferFromDownstreamBuyerVO buyerOrderVO) {
		this.buyerOrderVO = buyerOrderVO;
	}

	public List<TaobaoOfferToSupplierVO> getSupplierOrderVoList() {
		return supplierOrderVoList;
	}

	public void setSupplierOrderVoList(List<TaobaoOfferToSupplierVO> supplierOrderVoList) {
		this.supplierOrderVoList = supplierOrderVoList;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "TaobaoOfferStatisticsRowVO [buyerOrderVO=" + buyerOrderVO + ", supplierOrderVoList="
				+ supplierOrderVoList + ", profit=" + profit + "]";
	}

}
