package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramPhotoSizeDTO {

	private String file_id;
	private String file_unique_id;
	private Long width;
	private Long height;
	private Long file_size;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_unique_id() {
		return file_unique_id;
	}

	public void setFile_unique_id(String file_unique_id) {
		this.file_unique_id = file_unique_id;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	@Override
	public String toString() {
		return "TelegramPhotoSizeDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", width=" + width
				+ ", height=" + height + ", file_size=" + file_size + "]";
	}

}
