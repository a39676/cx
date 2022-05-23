package demo.joy.image.manager.pojo.result;

import demo.joy.common.pojo.result.JoyCommonResult;

public class JoyImageUploadResult extends JoyCommonResult {

	private String imgSavePath;

	private String filename;

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

	@Override
	public String toString() {
		return "JoyImageUploadResult [imgSavePath=" + imgSavePath + ", filename=" + filename + "]";
	}

}
