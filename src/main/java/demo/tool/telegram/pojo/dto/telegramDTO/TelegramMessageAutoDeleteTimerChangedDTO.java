package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramMessageAutoDeleteTimerChangedDTO {

	private Long message_auto_delete_time;

	public Long getMessage_auto_delete_time() {
		return message_auto_delete_time;
	}

	public void setMessage_auto_delete_time(Long message_auto_delete_time) {
		this.message_auto_delete_time = message_auto_delete_time;
	}

	@Override
	public String toString() {
		return "TelegramMessageAutoDeleteTimerChangedDTO [message_auto_delete_time=" + message_auto_delete_time + "]";
	}

}
