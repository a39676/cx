package demo.finance.cryptoCoin.tool.pojo.result;

import java.util.ArrayList;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class CryptoDataCompareRateResult extends CommonResult {

	private List<CryptoDataCompareRateSubResult> resultList;

	public List<CryptoDataCompareRateSubResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<CryptoDataCompareRateSubResult> resultList) {
		this.resultList = resultList;
	}
	
	public void addSubResult(CryptoDataCompareRateSubResult subResult) {
		if(this.resultList == null) {
			this.resultList = new ArrayList<>();
		};
		this.resultList.add(subResult);
	}

	@Override
	public String toString() {
		return "CryptoDataCompareRateResult [resultList=" + resultList + "]";
	}

}
