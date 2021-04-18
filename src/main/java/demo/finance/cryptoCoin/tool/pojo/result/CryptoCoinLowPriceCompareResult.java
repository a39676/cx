package demo.finance.cryptoCoin.tool.pojo.result;

import java.util.ArrayList;
import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.finance.cryptoCoin.tool.pojo.dto.CryptoCoinLowPriceCompareDTO;

public class CryptoCoinLowPriceCompareResult extends CommonResult {

	private List<CryptoCoinLowPriceCompareDTO> dataList;

	public List<CryptoCoinLowPriceCompareDTO> getDataList() {
		return dataList;
	}

	public void setDataList(List<CryptoCoinLowPriceCompareDTO> dataList) {
		this.dataList = dataList;
	}

	public void addData(CryptoCoinLowPriceCompareDTO dto) {
		if(this.dataList == null) {
			this.dataList = new ArrayList<>();
		}
		this.dataList.add(dto);
	}
	
	@Override
	public String toString() {
		return "CryptoCoinLowPriceCompareResult [dataList=" + dataList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", isFail()="
				+ isFail() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}

}
