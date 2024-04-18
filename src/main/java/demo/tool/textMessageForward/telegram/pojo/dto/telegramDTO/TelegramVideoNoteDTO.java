package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramVideoNoteDTO {

	private String file_id;
	private String file_unique_id;
	private Long length;
	private Long duration;
	private TelegramPhotoSizeDTO thumb;
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

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public TelegramPhotoSizeDTO getThumb() {
		return thumb;
	}

	public void setThumb(TelegramPhotoSizeDTO thumb) {
		this.thumb = thumb;
	}

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	@Override
	public String toString() {
		return "TelegramVideoNoteDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", length=" + length
				+ ", duration=" + duration + ", thumb=" + thumb + ", file_size=" + file_size + "]";
	}

}
