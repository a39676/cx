package demo.image.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

public class UploadImageToCloudinaryResult extends CommonResultCX {

	private String imgUrl;
	private Long imgId;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	@Override
	public String toString() {
		return "UploadImageToCloudinaryResult [imgUrl=" + imgUrl + ", imgId=" + imgId + "]";
	}

}
