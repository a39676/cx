package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramStickersDTO {

	private String file_id;
	private String file_unique_id;
	private String type;
	private Long width;
	private Long height;
	private Boolean is_animated;
	private Boolean is_video;
	private TelegramPhotoSizeDTO thumb;
	private String emoji;
	private String set_name;
	private TelegramFileDTO premium_animation;
	private TelegramMaskPositionDTO mask_position;
	private String custom_emoji_id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Boolean getIs_animated() {
		return is_animated;
	}

	public void setIs_animated(Boolean is_animated) {
		this.is_animated = is_animated;
	}

	public Boolean getIs_video() {
		return is_video;
	}

	public void setIs_video(Boolean is_video) {
		this.is_video = is_video;
	}

	public TelegramPhotoSizeDTO getThumb() {
		return thumb;
	}

	public void setThumb(TelegramPhotoSizeDTO thumb) {
		this.thumb = thumb;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public String getSet_name() {
		return set_name;
	}

	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}

	public TelegramFileDTO getPremium_animation() {
		return premium_animation;
	}

	public void setPremium_animation(TelegramFileDTO premium_animation) {
		this.premium_animation = premium_animation;
	}

	public TelegramMaskPositionDTO getMask_position() {
		return mask_position;
	}

	public void setMask_position(TelegramMaskPositionDTO mask_position) {
		this.mask_position = mask_position;
	}

	public String getCustom_emoji_id() {
		return custom_emoji_id;
	}

	public void setCustom_emoji_id(String custom_emoji_id) {
		this.custom_emoji_id = custom_emoji_id;
	}

	public Long getFile_size() {
		return file_size;
	}

	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}

	@Override
	public String toString() {
		return "TelegramStickersDTO [file_id=" + file_id + ", file_unique_id=" + file_unique_id + ", type=" + type
				+ ", width=" + width + ", height=" + height + ", is_animated=" + is_animated + ", is_video=" + is_video
				+ ", thumb=" + thumb + ", emoji=" + emoji + ", set_name=" + set_name + ", premium_animation="
				+ premium_animation + ", mask_position=" + mask_position + ", custom_emoji_id=" + custom_emoji_id
				+ ", file_size=" + file_size + "]";
	}

}
