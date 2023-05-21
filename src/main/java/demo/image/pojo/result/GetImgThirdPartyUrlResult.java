package demo.image.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class GetImgThirdPartyUrlResult extends CommonResult {

	private String imgThirdPartyUrl;

	public String getImgThirdPartyUrl() {
		return imgThirdPartyUrl;
	}

	public void setImgThirdPartyUrl(String imgThirdPartyUrl) {
		this.imgThirdPartyUrl = imgThirdPartyUrl;
	}

	@Override
	public String toString() {
		return "GetImgThirdPartyUrlResult [imgThirdPartyUrl=" + imgThirdPartyUrl + "]";
	}

}
