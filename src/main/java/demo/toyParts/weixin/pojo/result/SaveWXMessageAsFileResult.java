package demo.toyParts.weixin.pojo.result;

import demo.baseCommon.pojo.result.CommonResultCX;

public class SaveWXMessageAsFileResult extends CommonResultCX {

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "SaveWXMessageAsFileResult [filePath=" + filePath + "]";
	}

}
