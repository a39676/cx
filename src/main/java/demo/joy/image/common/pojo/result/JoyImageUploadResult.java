package demo.joy.image.common.pojo.result;

import demo.joy.common.pojo.result.JoyCommonResult;

public class JoyImageUploadResult extends JoyCommonResult {

	private String imgSavePath;

	private String filename;

	private Long imgId;

	public String getImgSavePath() {
		return imgSavePath;
	}

	public void setImgSavePath(String imgSavePath) {
		this.imgSavePath = imgSavePath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	@Override
	public String toString() {
		return "JoyImageUploadResult [imgSavePath=" + imgSavePath + ", filename=" + filename + ", imgId=" + imgId + "]";
	}

}
