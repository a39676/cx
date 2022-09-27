package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramPollOptionDTO {

	private String text;
	private Long voter_count;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getVoter_count() {
		return voter_count;
	}

	public void setVoter_count(Long voter_count) {
		this.voter_count = voter_count;
	}

	@Override
	public String toString() {
		return "TelegramPollOptionDTO [text=" + text + ", voter_count=" + voter_count + "]";
	}

}
