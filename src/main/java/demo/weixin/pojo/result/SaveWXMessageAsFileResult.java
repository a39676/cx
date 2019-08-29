package demo.weixin.pojo.result;

import demo.baseCommon.pojo.result.CommonResult;

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
