package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramVideoChatScheduledDTO {

	private Long start_date;

	public Long getStart_date() {
		return start_date;
	}

	public void setStart_date(Long start_date) {
		this.start_date = start_date;
	}

	@Override
	public String toString() {
		return "TelegramVideoChatScheduledDTO [start_date=" + start_date + "]";
	}

}
