package demo.finance.cryptoCoin.data.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import finance.cryptoCoin.pojo.bo.CryptoCoinBigMoveSummaryDataBO;

public class GetBigMoveSummaryDataResult extends CommonResult {

	private List<CryptoCoinBigMoveSummaryDataBO> dataList;
	private Integer total = 0;
	private Integer risingCounting = 0;
	private Integer fallingCounting = 0;

	public List<CryptoCoinBigMoveSummaryDataBO> getDataList() {
		return dataList;
	}

	public void setDataList(List<CryptoCoinBigMoveSummaryDataBO> dataList) {
		this.dataList = dataList;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRisingCounting() {
		return risingCounting;
	}

	public void setRisingCounting(Integer risingCounting) {
		this.risingCounting = risingCounting;
	}

	public Integer getFallingCounting() {
		return fallingCounting;
	}

	public void setFallingCounting(Integer fallingCounting) {
		this.fallingCounting = fallingCounting;
	}

}
