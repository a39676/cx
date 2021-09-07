package demo.finance.cryptoCoin.data.pojo.result;

import java.util.ArrayList;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.data.pojo.bo.CryptoCoinMABO;

public class CryptoCoinMAResult extends CommonResult {

	private List<CryptoCoinMABO> maList;

	public List<CryptoCoinMABO> getMaList() {
		return maList;
	}

	public void setMaList(List<CryptoCoinMABO> maList) {
		this.maList = maList;
	}
	
	public void addMA(CryptoCoinMABO bo) {
		if(this.maList == null) {
			this.maList = new ArrayList<>();
		}
		this.maList.add(bo);
	}

	@Override
	public String toString() {
		return "CryptoCoinMAResult [maList=" + maList + ", success=" + success + "]";
	}

}
