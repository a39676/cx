package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramMessageEntityDTO {

	private String type;
	private Long offset;
	private Long length;
	private String url;
	private TelegramUserDTO user;
	private String language;
	private String custom_emoji_id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TelegramUserDTO getUser() {
		return user;
	}

	public void setUser(TelegramUserDTO user) {
		this.user = user;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCustom_emoji_id() {
		return custom_emoji_id;
	}

	public void setCustom_emoji_id(String custom_emoji_id) {
		this.custom_emoji_id = custom_emoji_id;
	}

	@Override
	public String toString() {
		return "TelegramMessageEntityDTO [type=" + type + ", offset=" + offset + ", length=" + length + ", url=" + url
				+ ", user=" + user + ", language=" + language + ", custom_emoji_id=" + custom_emoji_id + "]";
	}

}
