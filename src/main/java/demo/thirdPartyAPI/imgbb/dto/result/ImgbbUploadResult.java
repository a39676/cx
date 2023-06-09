package demo.thirdPartyAPI.imgbb.dto.result;

import auxiliaryCommon.pojo.result.CommonResult;
import toolPack.imgbb.pojo.dto.ImgbbUploadResponseDTO;

public class ImgbbUploadResult extends CommonResult {

	private ImgbbUploadResponseDTO responseData;

	public ImgbbUploadResponseDTO getResponseData() {
		return responseData;
	}

	public void setResponseData(ImgbbUploadResponseDTO responseData) {
		this.responseData = responseData;
	}

	@Override
	public String toString() {
		return "ImgbbUploadResult [responseData=" + responseData + "]";
	}

}
