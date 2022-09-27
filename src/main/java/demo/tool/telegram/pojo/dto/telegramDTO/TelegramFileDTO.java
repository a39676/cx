package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramFileDTO {

	private String file_id;
	private String file_unique_id;
	private Long file_size;
	private String file_path;

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

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	@Override
	public String toString() {
		return "TelegramFileDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", file_size=" + file_size
				+ ", file_path=" + file_path + "]";
	}

}
