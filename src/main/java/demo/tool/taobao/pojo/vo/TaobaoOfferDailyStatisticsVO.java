package demo.tool.taobao.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaobaoOfferDailyStatisticsVO {

	private LocalDate statisticsDate; // 统计日期
	private String statisticsDateStr; // 统计日期String
	private BigDecimal buyerOrderAmount; // 当日买家订单总额
	private BigDecimal supplierOrderAmount; // 当日供应商订单总额
	private BigDecimal refundOrderAmount; // 当日退款总额
	private BigDecimal profit; // 当日盈利

	public LocalDate getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(LocalDate statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public String getStatisticsDateStr() {
		return statisticsDateStr;
	}

	public void setStatisticsDateStr(String statisticsDateStr) {
		this.statisticsDateStr = statisticsDateStr;
	}

	public BigDecimal getBuyerOrderAmount() {
		return buyerOrderAmount;
	}

	public void setBuyerOrderAmount(BigDecimal buyerOrderAmount) {
		this.buyerOrderAmount = buyerOrderAmount;
	}

	public BigDecimal getSupplierOrderAmount() {
		return supplierOrderAmount;
	}

	public void setSupplierOrderAmount(BigDecimal supplierOrderAmount) {
		this.supplierOrderAmount = supplierOrderAmount;
	}

	public BigDecimal getRefundOrderAmount() {
		return refundOrderAmount;
	}

	public void setRefundOrderAmount(BigDecimal refundOrderAmount) {
		this.refundOrderAmount = refundOrderAmount;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "TaobaoOfferDailyStatisticsVO [statisticsDate=" + statisticsDate + ", statisticsDateStr="
				+ statisticsDateStr + ", buyerOrderAmount=" + buyerOrderAmount + ", supplierOrderAmount="
				+ supplierOrderAmount + ", refundOrderAmount=" + refundOrderAmount + ", profit=" + profit + "]";
	}

}
