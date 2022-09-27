package demo.tool.telegram.pojo.dto.telegramDTO;

public class TelegramCallbackGameDTO {

	private Long user_id;
	private Long score;
	private Boolean force;
	private Boolean disable_edit_message;
	private Long chat_id;
	private Long message_id;
	private String inline_message_id;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Boolean getForce() {
		return force;
	}

	public void setForce(Boolean force) {
		this.force = force;
	}

	public Boolean getDisable_edit_message() {
		return disable_edit_message;
	}

	public void setDisable_edit_message(Boolean disable_edit_message) {
		this.disable_edit_message = disable_edit_message;
	}

	public Long getChat_id() {
		return chat_id;
	}

	public void setChat_id(Long chat_id) {
		this.chat_id = chat_id;
	}

	public Long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}

	public String getInline_message_id() {
		return inline_message_id;
	}

	public void setInline_message_id(String inline_message_id) {
		this.inline_message_id = inline_message_id;
	}

	@Override
	public String toString() {
		return "TelegramCallbackGameDTO [user_id=" + user_id + ", score=" + score + ", force=" + force
				+ ", disable_edit_message=" + disable_edit_message + ", chat_id=" + chat_id + ", message_id="
				+ message_id + ", inline_message_id=" + inline_message_id + "]";
	}

}
