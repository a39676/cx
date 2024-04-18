package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

public class TelegramChatPermissionsDTO {

	private Boolean can_send_messages;
	private Boolean can_send_media_messages;
	private Boolean can_send_polls;
	private Boolean can_send_other_messages;
	private Boolean can_add_web_page_previews;
	private Boolean can_change_info;
	private Boolean can_invite_users;
	private Boolean can_pin_messages;

	public Boolean getCan_send_messages() {
		return can_send_messages;
	}

	public void setCan_send_messages(Boolean can_send_messages) {
		this.can_send_messages = can_send_messages;
	}

	public Boolean getCan_send_media_messages() {
		return can_send_media_messages;
	}

	public void setCan_send_media_messages(Boolean can_send_media_messages) {
		this.can_send_media_messages = can_send_media_messages;
	}

	public Boolean getCan_send_polls() {
		return can_send_polls;
	}

	public void setCan_send_polls(Boolean can_send_polls) {
		this.can_send_polls = can_send_polls;
	}

	public Boolean getCan_send_other_messages() {
		return can_send_other_messages;
	}

	public void setCan_send_other_messages(Boolean can_send_other_messages) {
		this.can_send_other_messages = can_send_other_messages;
	}

	public Boolean getCan_add_web_page_previews() {
		return can_add_web_page_previews;
	}

	public void setCan_add_web_page_previews(Boolean can_add_web_page_previews) {
		this.can_add_web_page_previews = can_add_web_page_previews;
	}

	public Boolean getCan_change_info() {
		return can_change_info;
	}

	public void setCan_change_info(Boolean can_change_info) {
		this.can_change_info = can_change_info;
	}

	public Boolean getCan_invite_users() {
		return can_invite_users;
	}

	public void setCan_invite_users(Boolean can_invite_users) {
		this.can_invite_users = can_invite_users;
	}

	public Boolean getCan_pin_messages() {
		return can_pin_messages;
	}

	public void setCan_pin_messages(Boolean can_pin_messages) {
		this.can_pin_messages = can_pin_messages;
	}

	@Override
	public String toString() {
		return "TelegramChatPermissionsDTO [can_send_messages=" + can_send_messages + ", can_send_media_messages="
				+ can_send_media_messages + ", can_send_polls=" + can_send_polls + ", can_send_other_messages="
				+ can_send_other_messages + ", can_add_web_page_previews=" + can_add_web_page_previews
				+ ", can_change_info=" + can_change_info + ", can_invite_users=" + can_invite_users
				+ ", can_pin_messages=" + can_pin_messages + "]";
	}

}
