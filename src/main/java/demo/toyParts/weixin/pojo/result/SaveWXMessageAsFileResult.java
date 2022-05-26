package demo.toyParts.weixin.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class SaveWXMessageAsFileResult extends CommonResult {

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
