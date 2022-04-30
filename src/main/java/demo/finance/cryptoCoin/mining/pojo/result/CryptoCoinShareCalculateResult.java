package demo.finance.cryptoCoin.mining.pojo.result;

import java.util.ArrayList;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class CryptoCoinShareCalculateResult extends CommonResult {

	private List<CryptoCoinShareCalculateSubResult> caculateResultList;

	public List<CryptoCoinShareCalculateSubResult> getCaculateResultList() {
		return caculateResultList;
	}

	public void setCaculateResultList(List<CryptoCoinShareCalculateSubResult> caculateResultList) {
		this.caculateResultList = caculateResultList;
	}
	
	public void addCaculateResult(CryptoCoinShareCalculateSubResult caculateResultList) {
		if(this.caculateResultList == null) {
			this.caculateResultList = new ArrayList<>();
		}
		this.caculateResultList.add(caculateResultList);
	}

	@Override
	public String toString() {
		return "CryptoCoinShareCalculateResult [caculateResultList=" + caculateResultList + "]";
	}

}
