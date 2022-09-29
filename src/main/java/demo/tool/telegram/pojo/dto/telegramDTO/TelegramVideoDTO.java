package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramVideoDTO {

	private String file_id;
	private String file_unique_id;
	private Long width;
	private Long height;
	private Long duration;
	private TelegramPhotoSizeDTO thumb;
	private String file_name;
	private String mime_type;
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

	@Override
	public String toString() {
		return "TelegramVideoDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", width=" + width
				+ ", height=" + height + ", duration=" + duration + ", thumb=" + thumb + ", file_name=" + file_name
				+ ", mime_type=" + mime_type + ", file_size=" + file_size + "]";
	}

}
