package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramChatPhotoDTO {

	private String small_file_id;
	private String small_file_unique_id;
	private String big_file_id;
	private String big_file_unique_id;

	public String getSmall_file_id() {
		return small_file_id;
	}

	public void setSmall_file_id(String small_file_id) {
		this.small_file_id = small_file_id;
	}

	public String getSmall_file_unique_id() {
		return small_file_unique_id;
	}

	public void setSmall_file_unique_id(String small_file_unique_id) {
		this.small_file_unique_id = small_file_unique_id;
	}

	public String getBig_file_id() {
		return big_file_id;
	}

	public void setBig_file_id(String big_file_id) {
		this.big_file_id = big_file_id;
	}

	public String getBig_file_unique_id() {
		return big_file_unique_id;
	}

	public void setBig_file_unique_id(String big_file_unique_id) {
		this.big_file_unique_id = big_file_unique_id;
	}

	@Override
	public String toString() {
		return "TelegramChatPhotoDTO [small_file_id=" + small_file_id + ", small_file_unique_id=" + small_file_unique_id
				+ ", big_file_id=" + big_file_id + ", big_file_unique_id=" + big_file_unique_id + "]";
	}

}
