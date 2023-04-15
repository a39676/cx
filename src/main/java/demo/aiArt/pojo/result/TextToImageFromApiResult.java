package demo.aiArt.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class TextToImageFromApiResult extends CommonResult {

	/** image data in base64 */
	private List<String> imageList;

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		return "TextToImageFromApiResult [imageList=" + imageList + "]";
	}

}
