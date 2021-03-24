package demo.finance.cryptoCoin.tool.pojo.result;

import java.math.BigDecimal;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

public class CryptoDataCompareLineResult extends CommonResult implements Comparable<CryptoDataCompareLineResult> {

	private List<CryptoCoinPriceCommonDataBO> dataList1;
	private List<CryptoCoinPriceCommonDataBO> dataList2;

	private BigDecimal differentRate;

	public List<CryptoCoinPriceCommonDataBO> getDataList1() {
		return dataList1;
	}

	public void setDataList1(List<CryptoCoinPriceCommonDataBO> dataList1) {
		this.dataList1 = dataList1;
	}

	public List<CryptoCoinPriceCommonDataBO> getDataList2() {
		return dataList2;
	}

	public void setDataList2(List<CryptoCoinPriceCommonDataBO> dataList2) {
		this.dataList2 = dataList2;
	}

	public BigDecimal getDifferentRate() {
		return differentRate;
	}

	public void setDifferentRate(BigDecimal differentRate) {
		this.differentRate = differentRate;
	}

	@Override
	public String toString() {
		return "CryptoDataCompareResult [dataList1=" + dataList1 + ", dataList2=" + dataList2 + ", differentRate="
				+ differentRate + "]";
	}

	@Override
	public int compareTo(CryptoDataCompareLineResult o) {
		return compareStartTime(o, this);
	}

	private int compareStartTime(CryptoDataCompareLineResult o, CryptoDataCompareLineResult t) {
		if (o.getDifferentRate() == null && t.getDifferentRate() == null) {
			return 0;
		} else if (o.getDifferentRate() == null) {
			return -1;
		} else if (this.getDifferentRate() == null) {
			return 1;
		} else {
			return o.getDifferentRate().compareTo(this.getDifferentRate());
		}
	}

}
