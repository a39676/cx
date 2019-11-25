package demo.image.pojo.dto;

public class UploadImageToCloudinaryDTO {

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "UploadImageToCloudinaryDTO [filePath=" + filePath + "]";
	}

}
