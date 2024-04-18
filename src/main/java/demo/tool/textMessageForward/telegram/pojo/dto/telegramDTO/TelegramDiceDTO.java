package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramDiceDTO {

	private String emoji;
	private Long value;

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TelegramDiceDTO [emoji=" + emoji + ", value=" + value + "]";
	}

}
