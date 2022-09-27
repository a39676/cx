package demo.tool.telegram.pojo.dto;

import java.util.List;

import demo.tool.telegram.pojo.dto.telegramDTO.TelegramMessageDTO;

public class TelegramGetUpdateMessageResultDTO {

	private Long update_id;
	private List<TelegramMessageDTO> message;

	public Long getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(Long update_id) {
		this.update_id = update_id;
	}

	public List<TelegramMessageDTO> getMessage() {
		return message;
	}

	public void setMessage(List<TelegramMessageDTO> message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TelegramGetUpdateMessageDTO [update_id=" + update_id + ", message=" + message + "]";
	}

}
