package demo.image.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class GetImgUrlInBatchResult extends CommonResult {

	private List<String> listOfImgPkOrUrl;

	public List<String> getListOfImgPkOrUrl() {
		return listOfImgPkOrUrl;
	}

	public void setListOfImgPkOrUrl(List<String> listOfImgPkOrUrl) {
		this.listOfImgPkOrUrl = listOfImgPkOrUrl;
	}

	@Override
	public String toString() {
		return "GetImgUrlInBatchResult [listOfImgPkOrUrl=" + listOfImgPkOrUrl + "]";
	}

}
