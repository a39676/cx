package demo.tool.fakeFTP.pojo.param.controllerParam;

public class DownloadTargetFileParam {

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "GetFilePathDetailParam [filePath=" + filePath + "]";
	}

}
