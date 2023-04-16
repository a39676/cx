package demo.aiArt.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class TextToImageFromUiResult extends CommonResult {

	private List<String> imgUrlList;

	public List<String> getImgUrlList() {
		return imgUrlList;
	}

	public void setImgUrlList(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}

	@Override
	public String toString() {
		return "TextToImageFromUiResult [imgUrlList=" + imgUrlList + "]";
	}

}
