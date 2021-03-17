package demo.image.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class ImgHandleSrcDataResult extends CommonResult {

	private String base64Str;
	private String imgFileType;

	public String getBase64Str() {
		return base64Str;
	}

	public void setBase64Str(String base64Str) {
		this.base64Str = base64Str;
	}

	public String getImgFileType() {
		return imgFileType;
	}

	public void setImgFileType(String imgFileType) {
		this.imgFileType = imgFileType;
	}

	@Override
	public String toString() {
		return "ImgHandleSrcDataBO [base64Str=" + base64Str + ", imgFileType=" + imgFileType + "]";
	}

}
