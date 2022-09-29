package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramPassportFileDTO {

	private String file_id;
	private String file_unique_id;
	private Long file_size;
	private Long file_date;

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

	public Long getFile_date() {
		return file_date;
	}

	public void setFile_date(Long file_date) {
		this.file_date = file_date;
	}

	@Override
	public String toString() {
		return "TelegramPassportFileDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", file_size="
				+ file_size + ", file_date=" + file_date + "]";
	}

}
