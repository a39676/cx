package demo.tool.taobao.pojo.result;

import java.math.BigDecimal;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.taobao.pojo.vo.TaobaoOfferStatisticsRowVO;

public class TaobaoOfferStatisticsResult extends CommonResult {

	private List<TaobaoOfferStatisticsRowVO> statisticsList;
	private BigDecimal totalBuyerOrderAmount;
	private BigDecimal totalSupplierOrderAmount;
	private BigDecimal totalProfit;

	public List<TaobaoOfferStatisticsRowVO> getStatisticsList() {
		return statisticsList;
	}

	public void setStatisticsList(List<TaobaoOfferStatisticsRowVO> statisticsList) {
		this.statisticsList = statisticsList;
	}

	public BigDecimal getTotalBuyerOrderAmount() {
		return totalBuyerOrderAmount;
	}

	public void setTotalBuyerOrderAmount(BigDecimal totalBuyerOrderAmount) {
		this.totalBuyerOrderAmount = totalBuyerOrderAmount;
	}

	public BigDecimal getTotalSupplierOrderAmount() {
		return totalSupplierOrderAmount;
	}

	public void setTotalSupplierOrderAmount(BigDecimal totalSupplierOrderAmount) {
		this.totalSupplierOrderAmount = totalSupplierOrderAmount;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	@Override
	public String toString() {
		return "TaobaoOfferStatisticsResult [statisticsList=" + statisticsList + ", totalBuyerOrderAmount="
				+ totalBuyerOrderAmount + ", totalSupplierOrderAmount=" + totalSupplierOrderAmount + ", totalProfit="
				+ totalProfit + "]";
	}

}
