package demo.tool.textMessageForward.telegram.pojo.dto.telegramDTO;

import demo.tool.textMessageForward.telegram.pojo.type.TelegramChatType;

public class TelegramChatDTO {

	private Long id;
	/** {@link TelegramChatType} */
	private String type;
	private String title;
	private String username;
	private String first_name;
	private String last_name;
	private TelegramChatPhotoDTO photo;
	private String bio;
	private Boolean has_private_forwards;
	private Boolean has_restricted_voice_and_video_messages;
	private Boolean join_to_send_messages;
	private Boolean join_by_request;
	private String description;
	private String invite_link;
	private TelegramMessageDTO pinned_message;
	private TelegramChatPermissionsDTO permissions;
	private Long slow_mode_delay;
	private Long message_auto_delete_time;
	private Boolean has_protected_content;
	private String sticker_set_name;
	private Boolean can_set_sticker_set;
	private Long linked_chat_id;
	private TelegramChatLocationDTO location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public TelegramChatPhotoDTO getPhoto() {
		return photo;
	}

	public void setPhoto(TelegramChatPhotoDTO photo) {
		this.photo = photo;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Boolean getHas_private_forwards() {
		return has_private_forwards;
	}

	public void setHas_private_forwards(Boolean has_private_forwards) {
		this.has_private_forwards = has_private_forwards;
	}

	public Boolean getHas_restricted_voice_and_video_messages() {
		return has_restricted_voice_and_video_messages;
	}

	public void setHas_restricted_voice_and_video_messages(Boolean has_restricted_voice_and_video_messages) {
		this.has_restricted_voice_and_video_messages = has_restricted_voice_and_video_messages;
	}

	public Boolean getJoin_to_send_messages() {
		return join_to_send_messages;
	}

	public void setJoin_to_send_messages(Boolean join_to_send_messages) {
		this.join_to_send_messages = join_to_send_messages;
	}

	public Boolean getJoin_by_request() {
		return join_by_request;
	}

	public void setJoin_by_request(Boolean join_by_request) {
		this.join_by_request = join_by_request;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInvite_link() {
		return invite_link;
	}

	public void setInvite_link(String invite_link) {
		this.invite_link = invite_link;
	}

	public TelegramMessageDTO getPinned_message() {
		return pinned_message;
	}

	public void setPinned_message(TelegramMessageDTO pinned_message) {
		this.pinned_message = pinned_message;
	}

	public TelegramChatPermissionsDTO getPermissions() {
		return permissions;
	}

	public void setPermissions(TelegramChatPermissionsDTO permissions) {
		this.permissions = permissions;
	}

	public Long getSlow_mode_delay() {
		return slow_mode_delay;
	}

	public void setSlow_mode_delay(Long slow_mode_delay) {
		this.slow_mode_delay = slow_mode_delay;
	}

	public Long getMessage_auto_delete_time() {
		return message_auto_delete_time;
	}

	public void setMessage_auto_delete_time(Long message_auto_delete_time) {
		this.message_auto_delete_time = message_auto_delete_time;
	}

	public Boolean getHas_protected_content() {
		return has_protected_content;
	}

	public void setHas_protected_content(Boolean has_protected_content) {
		this.has_protected_content = has_protected_content;
	}

	public String getSticker_set_name() {
		return sticker_set_name;
	}

	public void setSticker_set_name(String sticker_set_name) {
		this.sticker_set_name = sticker_set_name;
	}

	public Boolean getCan_set_sticker_set() {
		return can_set_sticker_set;
	}

	public void setCan_set_sticker_set(Boolean can_set_sticker_set) {
		this.can_set_sticker_set = can_set_sticker_set;
	}

	public Long getLinked_chat_id() {
		return linked_chat_id;
	}

	public void setLinked_chat_id(Long linked_chat_id) {
		this.linked_chat_id = linked_chat_id;
	}

	public TelegramChatLocationDTO getLocation() {
		return location;
	}

	public void setLocation(TelegramChatLocationDTO location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "TelegramChatDTO [id=" + id + ", type=" + type + ", title=" + title + ", username=" + username
				+ ", first_name=" + first_name + ", last_name=" + last_name + ", photo=" + photo + ", bio=" + bio
				+ ", has_private_forwards=" + has_private_forwards + ", has_restricted_voice_and_video_messages="
				+ has_restricted_voice_and_video_messages + ", join_to_send_messages=" + join_to_send_messages
				+ ", join_by_request=" + join_by_request + ", description=" + description + ", invite_link="
				+ invite_link + ", pinned_message=" + pinned_message + ", permissions=" + permissions
				+ ", slow_mode_delay=" + slow_mode_delay + ", message_auto_delete_time=" + message_auto_delete_time
				+ ", has_protected_content=" + has_protected_content + ", sticker_set_name=" + sticker_set_name
				+ ", can_set_sticker_set=" + can_set_sticker_set + ", linked_chat_id=" + linked_chat_id + ", location="
				+ location + "]";
	}

}
