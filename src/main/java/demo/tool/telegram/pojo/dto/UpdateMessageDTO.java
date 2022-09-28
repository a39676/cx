package demo.tool.telegram.pojo.dto;

import demo.tool.telegram.pojo.dto.telegramDTO.TelegramMessageDTO;

public class UpdateMessageDTO {

	private Long update_id;
	private TelegramMessageDTO message;

	public Long getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(Long update_id) {
		this.update_id = update_id;
	}

	public TelegramMessageDTO getMessage() {
		return message;
	}

	public void setMessage(TelegramMessageDTO message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TelegramGetUpdateMessageResultDTO [update_id=" + update_id + ", message=" + message + "]";
	}

}
