package demo.tool.wodian.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.wodian.pojo.vo.WodianContractInfoVO;

public class WodianContractListResult extends CommonResult {

	private List<WodianContractInfoVO> contractList;

	public List<WodianContractInfoVO> getContractList() {
		return contractList;
	}

	public void setContractList(List<WodianContractInfoVO> contractList) {
		this.contractList = contractList;
	}

	@Override
	public String toString() {
		return "WodianContractListResult [contractList=" + contractList + "]";
	}

}
