package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramAudioDTO {

	private String file_id;
	private String file_unique_id;
	private Long duration;
	private String performer;
	private String title;
	private String file_name;
	private String mime_type;
	private Long file_size;
	private TelegramPhotoSizeDTO thumb;

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

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	public TelegramPhotoSizeDTO getThumb() {
		return thumb;
	}

	public void setThumb(TelegramPhotoSizeDTO thumb) {
		this.thumb = thumb;
	}

	@Override
	public String toString() {
		return "TelegramAudioDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", duration=" + duration
				+ ", performer=" + performer + ", title=" + title + ", file_name=" + file_name + ", mime_type="
				+ mime_type + ", file_size=" + file_size + ", thumb=" + thumb + "]";
	}

}
