package demo.image.pojo.result;

import java.util.Map;

import auxiliaryCommon.pojo.result.CommonResult;

public class GetImgThirdPartyUrlInBatchResult extends CommonResult {

	private Map<String, String> imgPkMatchUrl;

	public Map<String, String> getImgPkMatchUrl() {
		return imgPkMatchUrl;
	}

	public void setImgPkMatchUrl(Map<String, String> imgPkMatchUrl) {
		this.imgPkMatchUrl = imgPkMatchUrl;
	}

	@Override
	public String toString() {
		return "GetImgThirdPartyUrlInBatchResult [imgPkMatchUrl=" + imgPkMatchUrl + "]";
	}

}
